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
import com.trueandtrust.shoplex.model.DBModel
import com.trueandtrust.shoplex.model.adapter.MyImagesAdapter
import com.trueandtrust.shoplex.model.adapter.PropertyAdapter
import com.trueandtrust.shoplex.model.interfaces.INotifyMVP
import com.trueandtrust.shoplex.model.pojo.Product
import com.trueandtrust.shoplex.model.enumurations.*
import com.trueandtrust.shoplex.viewmodel.ProductVM
import com.trueandtrust.shoplex.model.pojo.Property
import com.trueandtrust.shoplex.view.dialogs.PropertyDialog


class AddProductActivity : AppCompatActivity(), INotifyMVP {
    private val OPEN_GALLERY_CODE = 200
    private val MAX_IMAGES_SIZE = 6
    private lateinit var binding: ActivityAddProductBinding
    private lateinit var viewModel: ProductVM
    private lateinit var product: Product
    private val database: DBModel = DBModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Define View Model
        viewModel = ViewModelProvider(this).get(ProductVM::class.java)
        viewModel.product.observe(this, {
            updateSliderUI()
            binding.imgSliderAddProduct.setImageList(
                viewModel.product.value!!.imageSlideList, ScaleTypes.CENTER_INSIDE
            )
        })

        viewModel.arrCategory.observe(this, {
            val arrayCategoryAdapter = ArrayAdapter(
                applicationContext,
                R.layout.dropdown_item,
                it
            )
            binding.actTVCategory.setAdapter(arrayCategoryAdapter)
        })

        viewModel.arrSubCategory.observe(this, {
            // SubCategory Dropdown

            val arraySubcategoryAdapter = ArrayAdapter(
                applicationContext,
                R.layout.dropdown_item,
                it
            )
            binding.actTVSubCategory.setAdapter(arraySubcategoryAdapter)

        })

        // Define product from view model
        product = viewModel.product.value!!

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
            } else
                Toast.makeText(this, "Max", Toast.LENGTH_SHORT).show()
        }

        // AddProduct Button
        binding.btnAddProduct.setOnClickListener {
            when {
                binding.edProductName.length() == 0 -> {
                    binding.tiProductName.error = getString(R.string.Required)
                    return@setOnClickListener
                }
                binding.edProductName.length() < 5 -> {
                    binding.tiProductName.error = getString(R.string.min_product_name_err)
                    return@setOnClickListener
                }
                binding.edDescription.length() == 0 -> {
                    binding.tiDescription.error = getString(R.string.Required)
                    return@setOnClickListener
                }
                binding.edDescription.length() < 30 -> {
                    binding.tiDescription.error =
                        getString(com.trueandtrust.shoplex.R.string.min_description_err)
                    return@setOnClickListener
                }
                binding.edOldPrice.length() == 0 -> {
                    binding.tiOldPrice.error = getString(R.string.Required)
                    return@setOnClickListener
                }
                binding.actTVCategory.text.isNullOrEmpty() -> {
                    binding.tiCategory.error = getString(R.string.Required)
                    return@setOnClickListener
                }
                binding.actTVSubCategory.text.isNullOrEmpty() -> {
                    binding.tiSubCategory.error = getString(R.string.Required)
                    return@setOnClickListener
                }
            }

            if (product.imagesListURI.isNullOrEmpty()) {
                Toast.makeText(this, "Please Select Your Product Images", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            product.name = binding.edProductName.text.toString()
            product.description = binding.edDescription.text.toString()
            product.price = binding.edOldPrice.text.toString().toFloat()
            product.newPrice = binding.edNewPrice.text.toString().toFloat()
            if (binding.edDiscountNum.text!!.isNotEmpty()) {
                product.discount = binding.edDiscountNum.text.toString().toInt()
            }

            product.category =
                binding.actTVCategory.text.toString().replace(
                    " ",
                    "_"
                ).toUpperCase()

            product.subCategory =
                binding.actTVSubCategory.text.toString().replace(
                    " ",
                    "_"
                ).toUpperCase()

            startActivity(Intent(this, ConfirmProductActivity::class.java).apply {
                this.putExtra(getString(R.string.PRODUCT_KEY), product)
            })

        }

        //Open Dialog Button
        binding.btnAddProperty.setOnClickListener {
            openPropertyDialog()
        }
    }

    private fun settingUpEditTexts() {
        // Product Name
        binding.edProductName.addTextChangedListener {
            binding.tiProductName.error = null
            //product.name = binding.edProductName.text.toString()
        }

        // Description
        binding.edDescription.addTextChangedListener {
            binding.tiDescription.error = null
            //product.description = binding.edDescription.text.toString()
        }

        // Discount
        binding.edDiscountNum.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count > 0 && !binding.edDiscountNum.text.isNullOrEmpty() && !binding.edOldPrice.text.isNullOrEmpty()) {
                    val newPrice = (binding.edOldPrice.text.toString()
                        .toFloat() - (binding.edOldPrice.text.toString()
                        .toFloat() * (binding.edDiscountNum.text.toString()
                        .toInt() / 100.0F)))

                    binding.edNewPrice.text = "%.2f".format(newPrice)
                } else {
                    binding.edNewPrice.text =
                        binding.edOldPrice.text.toString().toFloat().toString()
                }
            }
        })

        binding.tvDiscount.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (binding.edDiscountNum.text.toString().toFloat() > 90) {
                    binding.edDiscountNum.setText(getString(R.string.maxDiscount))
                }
            }
        }

        // Old Price
        binding.edOldPrice.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count > 0 && !binding.edDiscountNum.text.isNullOrEmpty() && binding.edOldPrice.text.toString()
                        .toFloat() >= 10
                ) {
                    val newPrice = (binding.edOldPrice.text.toString()
                        .toFloat() - (binding.edOldPrice.text.toString()
                        .toFloat() * (binding.edDiscountNum.text.toString()
                        .toInt() / 100.0F)))
                    binding.edNewPrice.text = "%.2f".format(newPrice)
                } else if (count > 0 && binding.edDiscountNum.text.isNullOrEmpty()) {
                    if (binding.edOldPrice.text.toString().toFloat() >= 10) {
                        binding.edNewPrice.text =
                            binding.edOldPrice.text.toString().toFloat().toString()
                    } else {
                        binding.edNewPrice.text = getString(R.string.minPrice).toFloat().toString()
                    }
                } else {
                    binding.edNewPrice.text = getString(R.string.minPrice).toFloat().toString()
                }
            }
        })

        binding.edOldPrice.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (binding.edOldPrice.text.isNullOrEmpty() || binding.edOldPrice.text.toString()
                        .toFloat() < 10
                ) {
                    binding.edOldPrice.setText(getString(R.string.minPrice))
                }
            }
        }

        binding.actTVCategory.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                binding.tiCategory.error = null
                binding.actTVSubCategory.text = null
                val selectedItem = parent.getItemAtPosition(position).toString()

                viewModel.getSubCategory(selectedItem)

                binding.actTVSubCategory.onItemClickListener =
                    OnItemClickListener { parent, view, position, id ->
                        binding.tiSubCategory.error = null
                    }
            }
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
                        product.images.add(null)
                        database.addImage(imageUri, product.productID, product.images.count() - 1)
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
                    product.images.add(null)
                    database.addImage(imageUri, product.productID, product.images.count() - 1)
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
        if(product.images[position] != null) {
            product.imageSlideList.removeAt(position)
            product.imagesListURI.removeAt(position)
            database.removeImage(product.images.removeAt(position)!!, this)

            binding.imgSliderAddProduct.setImageList(
                product.imageSlideList,
                ScaleTypes.CENTER_INSIDE
            )
            binding.rvUploadImages.adapter?.notifyDataSetChanged()
            updateSliderUI()
        }else{
            Toast.makeText(this, "Please wait until image uploaded, then remove!", Toast.LENGTH_SHORT).show()
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

    private fun openPropertyDialog() {
        val propertyDialog = PropertyDialog(this)
        propertyDialog.show(supportFragmentManager, "Property Dialog")
    }

    override fun applyData(property: Property) {
        product.properties.add(property)
        binding.rcProperty.adapter!!.notifyDataSetChanged()
    }

    override fun onPropertyRemoved(position: Int) {
        super.onPropertyRemoved(position)
    }

    override fun onImageUploadedSuccess(path: String, position: Int) {
        product.images[position] = path
    }

    override fun onImageUploadedFailed(position: Int) {
        Toast.makeText(this, "Failed to upload Num: " + (position + 1), Toast.LENGTH_SHORT).show()
        product.images.removeAt(position)
    }
}
