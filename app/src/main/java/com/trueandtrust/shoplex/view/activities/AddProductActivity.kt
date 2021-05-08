package com.trueandtrust.shoplex.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.adapters.ColorAdapter
import com.trueandtrust.shoplex.adapters.ImageAdapter
import com.trueandtrust.shoplex.databinding.ActivityAddProductBinding

class AddProductActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddProductBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var imageAdapter: ImageAdapter

    val images = arrayOf(R.drawable.two,R.drawable.product_two,R.drawable.one,R.drawable.two,R.drawable.product_two,R.drawable.one)
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
        val arrayCategoryAdapter = ArrayAdapter(this, R.layout.dropdown_item,arrCategory)
        binding.actDropdownItem.setAdapter(arrayCategoryAdapter)
        //SubCategory Dropdown
        val arrSubcategory = resources.getStringArray(R.array.arr_subcategory)
        val arraySubcategoryAdapter = ArrayAdapter(this, R.layout.dropdown_item,arrSubcategory)
        binding.actDropdownSubItem.setAdapter(arraySubcategoryAdapter)
    }
}