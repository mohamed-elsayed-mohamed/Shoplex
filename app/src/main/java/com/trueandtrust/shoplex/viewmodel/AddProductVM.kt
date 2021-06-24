package com.trueandtrust.shoplex.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.model.enumurations.*
import com.trueandtrust.shoplex.model.pojo.Product

class AddProductVM(val context: Context) : ViewModel() {
    var product: MutableLiveData<Product> = MutableLiveData()
    var arrCategory: MutableLiveData<Array<String>> = MutableLiveData()
    var arrSubCategory: MutableLiveData<Array<String>> = MutableLiveData()

    init {
        product.value = Product()
    }

    fun getCategory(){
        this.arrCategory.value = context.resources.getStringArray(R.array.categories)
            //Category.values().map { it.toString().replace("_", " ") }.toTypedArray()
    }

    fun getSubCategory(selectedCategory: Category){
         val listSubCat =
            when(selectedCategory) {
                Category.Fashion -> context.resources.getStringArray(R.array.categories)
                Category.Health_Care -> SubHealth.values()
                Category.Phone_and_Tablets -> SubPhone.values()
                Category.Electronics -> SubElectronic.values()
                Category.Accessories -> SubAccessors.values()
                Category.Books -> SubBook.values()
            }

        this.arrSubCategory.value = (listSubCat as Array<*>).map {
            it.toString().replace("_", " ")
        }.toTypedArray()
    }
}