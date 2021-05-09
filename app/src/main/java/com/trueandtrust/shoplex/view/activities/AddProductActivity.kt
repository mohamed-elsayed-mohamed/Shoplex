package com.trueandtrust.shoplex.view.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
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
    private lateinit var binding : ActivityAddProductBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var imageAdapter: ImageAdapter

    private lateinit var viewModel: AddProductVM

    val images = arrayOf(R.drawable.two,R.drawable.product_two,R.drawable.one,R.drawable.two,R.drawable.product_two,R.drawable.one)
    val imageList = ArrayList<SlideModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(AddProductVM::class.java)

        //ImageSlider
        imageList.add(SlideModel("https://bit.ly/2YoJ77H"))
        imageList.add(SlideModel("https://bit.ly/2BteuF2"))
        imageList.add(SlideModel("https://bit.ly/3fLJf72"))

        binding.imgSliderAddProduct.setImageList(imageList, ScaleTypes.CENTER_CROP)

        val myAdapter = MyImagesAdapter(viewModel.product.value!!.images, this)
        binding.rvUploadImages.adapter = myAdapter

        viewModel.product.observe(this, {
            binding.imgSliderAddProduct.setImageList(imageList, ScaleTypes.CENTER_CROP)
            binding.rvUploadImages.adapter?.notifyDataSetChanged()
        })


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
        val arrayCategoryAdapter = ArrayAdapter(this, R.layout.dropdown_item,arrCategory)
        binding.actDropdownItem.setAdapter(arrayCategoryAdapter)
        //SubCategory Dropdown
        val arrSubcategory = resources.getStringArray(R.array.arr_subcategory)
        val arraySubcategoryAdapter = ArrayAdapter(this, R.layout.dropdown_item,arrSubcategory)
        binding.actDropdownSubItem.setAdapter(arraySubcategoryAdapter)

        // AddImage Button
        binding.btnAddProductImages.setOnClickListener {
            openGalleryForImages()
        }

        //AddProduct Button
        binding.btnAddProduct.setOnClickListener{


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

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            // if multiple images are selected
            var product: Product = viewModel.product.value!!

            if (data?.clipData != null) {
                var count = data.clipData?.itemCount

                for (i in 0 until count!!) {
                    var imageUri: Uri = data.clipData?.getItemAt(i)!!.uri

                    if(!product.images.contains(imageUri)) {
                        product.images.add(imageUri)
                        imageList.add(SlideModel(imageUri.toString()))
                    }
                }
            } else if (data?.data != null) {
                // if single image is selected

                var imageUri: Uri = data.data!!
                if(!product.images.contains(imageUri)) {
                    product.images.add(imageUri)
                    imageList.add(SlideModel(imageUri.toString()))
                }
            }
           binding.imgSliderAddProduct.setImageList(imageList, ScaleTypes.CENTER_INSIDE)
            binding.rvUploadImages.adapter?.notifyDataSetChanged()
        }
    }

    override fun onRemoveItem(position: Int) {
        imageList.removeAt(position)
        binding.imgSliderAddProduct.setImageList(imageList, ScaleTypes.CENTER_INSIDE)
        viewModel.product.value!!.images.removeAt(position)
        binding.rvUploadImages.adapter?.notifyDataSetChanged()
    }

}