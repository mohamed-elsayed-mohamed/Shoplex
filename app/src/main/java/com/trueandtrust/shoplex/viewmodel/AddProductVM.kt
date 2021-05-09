package com.trueandtrust.shoplex.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trueandtrust.shoplex.model.pojo.Product

class AddProductVM: ViewModel {
    var product: MutableLiveData<Product> = MutableLiveData()

    constructor(){
        product.value = Product()
    }
}