package com.trueandtrust.shoplex.view.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ActivityAddProductBinding
import com.trueandtrust.shoplex.model.adapter.MyImagesAdapter
import com.trueandtrust.shoplex.model.adapter.PropertyAdapter
import com.trueandtrust.shoplex.model.interfaces.INotifyMVP
import com.trueandtrust.shoplex.model.pojo.Product
import com.trueandtrust.shoplex.model.enumurations.*
import com.trueandtrust.shoplex.viewmodel.AddProductVM
import com.trueandtrust.shoplex.model.pojo.Property
import com.trueandtrust.shoplex.view.dialogs.PropertyDialog

class AddProductActivity : AppCompatActivity(), INotifyMVP {
    private val OPEN_GALLERY_CODE = 200
    private val MAX_IMAGES_SIZE = 6
    private lateinit var binding: ActivityAddProductBinding
    private lateinit var viewModel: AddProductVM
    private lateinit var product: Product
    private var isUpdate: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Define View Model
        viewModel = ViewModelProvider(this).get(AddProductVM::class.java)

        viewModel.product.observe(this, {
            updateSliderUI()
            binding.imgSliderAddProduct.setImageList(
                viewModel.product.value!!.imageSlideList, ScaleTypes.CENTER_INSIDE
            )
        })

        viewModel.arrCategory.observe(this, {
            // Category Dropdown
            val arrayCategoryAdapter = ArrayAdapter(applicationContext, R.layout.dropdown_item, it)
            binding.actTVCategory.setAdapter(arrayCategoryAdapter)
        })

        viewModel.arrSubCategory.observe(this, {
            // SubCategory Dropdown
            val arraySubcategoryAdapter = ArrayAdapter(applicationContext, R.layout.dropdown_item, it)
            binding.actTVSubCategory.setAdapter(arraySubcategoryAdapter)
        })

        if(intent.hasExtra(getString(R.string.PRODUCT_KEY))){
            // User need to update data
            this.viewModel.product.value = intent.getParcelableExtra(getString(R.string.PRODUCT_KEY))
            this.product = this.viewModel.product.value!!
            onUpdate(product)
        }else{
            // Define product from view model
            product = viewModel.product.value!!
        }

        binding.product = product

        // Images Adapter
        val myAdapter = MyImagesAdapter(viewModel.product.value!!.imagesListURI, this)
        binding.rvUploadImages.adapter = myAdapter

        // Property Adapter
        val propAdapter = PropertyAdapter(product.properties)
        binding.rcProperty.adapter = propAdapter

        // Category Dropdown
        viewModel.getCategory()

        settingUpButtons()

        settingUpEditTexts()
    }

    private fun settingUpButtons() {
        // AddImage Button
        binding.btnAddProductImages.setOnClickListener {
            if (product.imagesListURI.count() < MAX_IMAGES_SIZE) {
                openGalleryForImages()
            } else {
                Toast.makeText(this, "Max", Toast.LENGTH_SHORT).show()
            }
        }

        //Open Dialog Button
        binding.btnAddProperty.setOnClickListener {
            val propertyDialog = PropertyDialog(this)
            propertyDialog.show(supportFragmentManager, "Property Dialog")
        }

        // AddProduct Button
        binding.btnAddProduct.setOnClickListener {
            if(!validateInput())
                return@setOnClickListener

            product.category = binding.actTVCategory.text.toString()
            product.subCategory = binding.actTVSubCategory.text.toString()

            startActivity(Intent(this, ConfirmProductActivity::class.java).apply {
                this.putExtra(getString(R.string.PRODUCT_KEY), product)
                this.putExtra(getString(R.string.update_product), isUpdate)
            })
        }
    }

    private fun settingUpEditTexts() {
        // Product Name
        binding.edProductName.addTextChangedListener {
            binding.tiProductName.error = null
        }

        // Description
        binding.edDescription.addTextChangedListener {
            binding.tiDescription.error = null
        }

        // Discount
        binding.edDiscountNum.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.edNewPrice.text = product.calculateNewPrice()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.edDiscountNum.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if(binding.edDiscountNum.text.isNullOrEmpty())
                    binding.edDiscountNum.setText(getString(R.string.zero))
                if (binding.edDiscountNum.text.toString().toFloat() > 90) {
                    binding.edDiscountNum.setText(getString(R.string.maxDiscount))
                }
            }
        }

        // Old Price
        binding.edOldPrice.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.edNewPrice.text = product.calculateNewPrice()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.edOldPrice.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (binding.edOldPrice.text.isNullOrEmpty() || binding.edOldPrice.text.toString().toFloat() < 10) {
                    binding.edOldPrice.setText(getString(R.string.minPrice))
                }
            }
        }

        // Category dropdown
        binding.actTVCategory.onItemClickListener =
            OnItemClickListener { parent, _, position, _ ->
                binding.tiCategory.error = null
                binding.actTVSubCategory.text = null
                val selectedItem = parent.getItemAtPosition(position).toString()

                viewModel.getSubCategory(selectedItem)

                binding.actTVSubCategory.onItemClickListener =
                    OnItemClickListener { _, _, _, _ ->
                        binding.tiSubCategory.error = null
                    }
            }
    }

    private fun onUpdate(product: Product) {
        this.isUpdate = true
        product.imageSlideList.clear()
        product.imagesListURI.clear()
        for(imgURL in product.images){
            product.imageSlideList.add(SlideModel(imgURL))
            product.imagesListURI.add(Uri.parse(imgURL))
        }

        binding.actTVCategory.setText(product.category)
        viewModel.getSubCategory(product.category)
        binding.actTVSubCategory.setText(product.subCategory)

        binding.btnAddProduct.text = getString(R.string.update_product)
    }

    private fun openGalleryForImages() {
        var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.type = "image/*"
        startActivityForResult(intent, OPEN_GALLERY_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == OPEN_GALLERY_CODE) {
            // if multiple images are selected

            if (data?.clipData != null) {
                var count = data.clipData?.itemCount

                for (i in 0 until count!!) {
                    var imageUri: Uri = data.clipData?.getItemAt(i)!!.uri

                    if (!product.imagesListURI.contains(imageUri) && product.imagesListURI.count() < MAX_IMAGES_SIZE) {
                        product.imagesListURI.add(imageUri)
                        product.imageSlideList.add(SlideModel(imageUri.toString()))
                    } else if (product.imagesListURI.count() >= MAX_IMAGES_SIZE) {
                        Toast.makeText(this, "Max", Toast.LENGTH_SHORT).show()
                    }
                }
            } else if (data?.data != null) {
                // if single image is selected

                var imageUri: Uri = data.data!!
                if (!product.imagesListURI.contains(imageUri)) {
                    product.imagesListURI.add(imageUri)
                    product.imageSlideList.add(SlideModel(imageUri.toString()))
                }
            }
            binding.imgSliderAddProduct.setImageList(
                product.imageSlideList,
                ScaleTypes.CENTER_INSIDE
            )
            binding.rvUploadImages.adapter?.notifyDataSetChanged()
            updateSliderUI()
        }
    }

    override fun onImageRemoved(position: Int) {
        if (product.imageSlideList[position] != null) {
            product.imageSlideList.removeAt(position)
            product.imagesListURI.removeAt(position)
            //database.removeImage(product.images.removeAt(position)!!, isUpdate)
            if (product.images.count() > 0)
                product.removedImages.add(product.images.removeAt(position)!!)

            binding.imgSliderAddProduct.setImageList(
                product.imageSlideList,
                ScaleTypes.CENTER_INSIDE
            )

            binding.rvUploadImages.adapter?.notifyDataSetChanged()
            updateSliderUI()
        } else {
            Toast.makeText(
                this,
                "Please wait until image uploaded, then remove!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun updateSliderUI() {
        val param = binding.rvUploadImages.layoutParams as ViewGroup.MarginLayoutParams

        if (product.imageSlideList.count() == 0) {
            binding.imgSliderAddProduct.setBackgroundResource(R.drawable.choose_product)
            binding.rvUploadImages.background = null
            param.setMargins(0, 0, 0, 0)
        } else {
            binding.imgSliderAddProduct.background = null
            binding.rvUploadImages.setBackgroundResource(R.drawable.ed_style)
            val margin_16 = resources.getDimension(R.dimen.margin_16).toInt()
            param.setMargins(margin_16, margin_16, margin_16, 0)
        }

        binding.rvUploadImages.layoutParams = param
    }

    override fun onNewPropertyAdded(property: Property) {
        product.properties.add(property)
        binding.rcProperty.adapter!!.notifyDataSetChanged()
    }

    private fun validateInput(): Boolean {
        when {
            binding.edProductName.length() == 0 -> {
                binding.tiProductName.error = getString(R.string.Required)
                return false
            }
            binding.edProductName.length() < 5 -> {
                binding.tiProductName.error = getString(R.string.min_product_name_err)
                return false
            }
            binding.edDescription.length() == 0 -> {
                binding.tiDescription.error = getString(R.string.Required)
                return false
            }
            binding.edDescription.length() < 30 -> {
                binding.tiDescription.error =
                    getString(com.trueandtrust.shoplex.R.string.min_description_err)
                return false
            }
            binding.edOldPrice.length() == 0 -> {
                binding.tiOldPrice.error = getString(R.string.Required)
                return false
            }
            binding.actTVCategory.text.isNullOrEmpty() -> {
                binding.tiCategory.error = getString(R.string.Required)
                return false
            }
            binding.actTVSubCategory.text.isNullOrEmpty() -> {
                binding.tiSubCategory.error = getString(R.string.Required)
                return false
            }
        }

        if (product.imagesListURI.isNullOrEmpty()) {
            Toast.makeText(this, "Please Select Your Product Images", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}