package com.trueandtrust.shoplex.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ActivityAddProductBinding

class AddProductActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

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