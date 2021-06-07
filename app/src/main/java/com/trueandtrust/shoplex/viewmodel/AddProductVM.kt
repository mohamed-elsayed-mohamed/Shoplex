package com.trueandtrust.shoplex.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trueandtrust.shoplex.model.enumurations.*
import com.trueandtrust.shoplex.model.pojo.Product

class AddProductVM: ViewModel {
    var product: MutableLiveData<Product> = MutableLiveData()
    var arrCategory: MutableLiveData<Array<String>> = MutableLiveData()
    var arrSubCategory: MutableLiveData<Array<String>> = MutableLiveData()

    constructor(){
        product.value = Product()
    }

    fun getCategory(){
        this.arrCategory.value = Category.values().map {
            it.toString().split("_").joinToString(" ")
        }.toTypedArray()
    }

    fun getSubCategory(selectedItem: String){
         val listSubCat =
            when(Category.valueOf(selectedItem.replace(" ", "_"))) {
                Category.Fashion -> SubFashion.values()
                Category.Health_Care -> SubHealth.values()
                Category.Phone_and_Tablets -> SubPhone.values()
                Category.Electronics -> SubElectronic.values()
                Category.Accessories -> SubAccessors.values()
                Category.Books -> SubBook.values()
            }

        this.arrSubCategory.value = (listSubCat as Array<Any>).map {
            it.toString().split("_").joinToString(" ")
        }.toTypedArray()
    }
}