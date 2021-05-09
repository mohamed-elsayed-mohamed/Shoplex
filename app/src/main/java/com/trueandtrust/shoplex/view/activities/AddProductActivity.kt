package com.trueandtrust.shoplex.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.trueandtrust.shoplex.R

import com.trueandtrust.shoplex.adapters.ImageAdapter
import com.trueandtrust.shoplex.databinding.ActivityAddProductBinding

class AddProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddProductBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var imageAdapter: ImageAdapter
    private var count = 0

    val images = arrayOf(
        R.drawable.two,
        R.drawable.product_two,
        R.drawable.one,
        R.drawable.two,
        R.drawable.product_two,
        R.drawable.one
    )
    val imageList = ArrayList<SlideModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //ImageSlider
        imageList.add(SlideModel("https://bit.ly/2YoJ77H"))
        imageList.add(SlideModel("https://bit.ly/2BteuF2"))
        imageList.add(SlideModel("https://bit.ly/3fLJf72"))

        binding.imgSliderAddProduct.setImageList(imageList, ScaleTypes.CENTER_CROP)

        //RecyclerView

        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        binding.recyclerViewImage.layoutManager = linearLayoutManager

        imageAdapter = ImageAdapter(images)
        binding.recyclerViewImage.adapter = imageAdapter

        //Category Dropdown
        val arrCategory = resources.getStringArray(R.array.arr_category)
        val arrayCategoryAdapter = ArrayAdapter(this, R.layout.dropdown_item, arrCategory)
        binding.actDropdownItem.setAdapter(arrayCategoryAdapter)
        //SubCategory Dropdown
        val arrSubcategory = resources.getStringArray(R.array.arr_subcategory)
        val arraySubcategoryAdapter = ArrayAdapter(this, R.layout.dropdown_item, arrSubcategory)
        binding.actDropdownSubItem.setAdapter(arraySubcategoryAdapter)


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
}