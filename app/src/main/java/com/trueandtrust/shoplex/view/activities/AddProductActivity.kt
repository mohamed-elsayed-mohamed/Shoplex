package com.trueandtrust.shoplex.view.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.adapters.ImageAdapter
import com.trueandtrust.shoplex.databinding.ActivityAddProductBinding
import com.trueandtrust.shoplex.model.adapter.MyImagesAdapter
import com.trueandtrust.shoplex.model.interfaces.ImagesChanges
import com.trueandtrust.shoplex.model.pojo.Product
import com.trueandtrust.shoplex.viewmodel.AddProductVM

class AddProductActivity : AppCompatActivity(), ImagesChanges {
    private val REQUEST_CODE = 200
    private lateinit var binding: ActivityAddProductBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var imageAdapter: ImageAdapter
    private var count = 0

    private lateinit var viewModel: AddProductVM
    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(AddProductVM::class.java)

        val myAdapter = MyImagesAdapter(viewModel.product.value!!.images, this)
        binding.rvUploadImages.adapter = myAdapter

        viewModel.product.observe(this, {
            binding.imgSliderAddProduct.setImageList(viewModel.product.value!!.imageSlideList, ScaleTypes.CENTER_INSIDE)
            binding.rvUploadImages.adapter?.notifyDataSetChanged()
        })

        product = viewModel.product.value!!


        /*
        //RecyclerView

        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        binding.recyclerViewImage.layoutManager = linearLayoutManager

        imageAdapter = ImageAdapter(images)
        binding.recyclerViewImage.adapter = imageAdapter

        */

        //Category Dropdown
        val arrCategory = resources.getStringArray(R.array.arr_category)
        val arrayCategoryAdapter = ArrayAdapter(this, R.layout.dropdown_item, arrCategory)
        binding.actDropdownItem.setAdapter(arrayCategoryAdapter)
        //SubCategory Dropdown
        val arrSubcategory = resources.getStringArray(R.array.arr_subcategory)
        val arraySubcategoryAdapter = ArrayAdapter(this, R.layout.dropdown_item, arrSubcategory)
        binding.actDropdownSubItem.setAdapter(arraySubcategoryAdapter)

        // AddImage Button
        binding.btnAddProductImages.setOnClickListener {
            openGalleryForImages()
        }

        //AddProduct Button
        binding.btnAddProduct.setOnClickListener {

            if (binding.edProductName.length() <= 10) {
                binding.edProductName.error = "Minimum Input is 10 Characters"
            }
            if (binding.edProductName.length() == 0) {
                binding.edProductName.error = "Please Enter Product Name"
            }
            if (binding.edmultiDescription.length() <= 30) {
                binding.edmultiDescription.error = "Minimum Input is 30 Characters"
            }
            if (binding.edmultiDescription.length() == 0) {
                binding.edmultiDescription.error = "Please Enter Product Description"
            }
            if (binding.edOldPrice.length() == 0) {
                binding.edOldPrice.error = "Please Enter The Price"
            }
        }
        //plus button
        binding.imgPlus.setOnClickListener {
            if (count >= 0 && count < 80) {
                count += 5
                binding.tvDiscountNum.text = "${count}%"
                binding.tvDiscountNum.error = null
            } else {
                binding.tvDiscountNum.error = "The Maximum Is 80%"
            }
        }
        //mins button
        binding.imgMunis.setOnClickListener {
            if (count > 0) {

            if (count > 0) {
                count -= 5
                binding.tvDiscountNum.text = "$count%"
                binding.tvDiscountNum.error = null
            } else if(count < 0 ){
                binding.tvDiscountNum.error = null
            } else {
                binding.tvDiscountNum.error = "The Maximum Is 0%"
            }
        }
    }

    private fun openGalleryForImages() {
        var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            // if multiple images are selected


            if (data?.clipData != null) {
                var count = data.clipData?.itemCount

                for (i in 0 until count!!) {
                    var imageUri: Uri = data.clipData?.getItemAt(i)!!.uri

                    if (!product.images.contains(imageUri)) {
                        product.images.add(imageUri)
                        product.imageSlideList.add(SlideModel(imageUri.toString()))
                    }
                }
            } else if (data?.data != null) {
                // if single image is selected

                var imageUri: Uri = data.data!!
                if (!product.images.contains(imageUri)) {
                    product.images.add(imageUri)
                    product.imageSlideList.add(SlideModel(imageUri.toString()))
                }
            }
            binding.imgSliderAddProduct.setImageList(product.imageSlideList, ScaleTypes.CENTER_INSIDE)
            binding.rvUploadImages.adapter?.notifyDataSetChanged()
        }
    }

    override fun onRemoveItem(position: Int) {
        viewModel.product.value!!.imageSlideList.removeAt(position)
        viewModel.product.value!!.images.removeAt(position)

        binding.imgSliderAddProduct.setImageList(product.imageSlideList, ScaleTypes.CENTER_INSIDE)
        binding.rvUploadImages.adapter?.notifyDataSetChanged()
    }
}