package com.trueandtrust.shoplex.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trueandtrust.shoplex.model.enumurations.*
import com.trueandtrust.shoplex.model.pojo.Product

class ProductVM: ViewModel {
    var product: MutableLiveData<Product> = MutableLiveData()
    var arrCategory: MutableLiveData<List<String>> = MutableLiveData()
    var arrSubCategory: MutableLiveData<List<String>> = MutableLiveData()

    constructor(){
        product.value = Product()
    }

    fun getCategory(){
        this.arrCategory.value = Category.values().map {
            it.toString().split("_").joinToString(" ") { wrd -> wrd.toLowerCase().capitalize() }
        }
    }

    fun getSubCategory(selectedItem: String){
         val listSubCat =
            when(Category.valueOf(selectedItem.replace(" ", "_").toUpperCase())) {
                Category.FASHION -> SubFashion.values().toList()
                Category.HEALTH_CARE -> SubHealth.values().toList()
                Category.PHONE_AND_TABLETS -> SubPhone.values().toList()
                Category.ELECTRONICS -> SubElectronic.values().toList()
                Category.ACCESSORIES -> SubAccessors.values().toList()
                Category.BOOKS -> SubBook.values().toList()
            }

        this.arrSubCategory.value = (listSubCat as List<Any>).map {
            it.toString().split("_").joinToString(" ") { wrd -> wrd.toLowerCase().capitalize() }
        }
    }
}