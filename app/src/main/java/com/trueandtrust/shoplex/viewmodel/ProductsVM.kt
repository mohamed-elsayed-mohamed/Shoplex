package com.trueandtrust.shoplex.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trueandtrust.shoplex.model.firebase.ProductsDBModel
import com.trueandtrust.shoplex.model.interfaces.INotifyMVP
import com.trueandtrust.shoplex.model.pojo.Product
import com.trueandtrust.shoplex.model.pojo.Review

class ProductsVM: ViewModel, INotifyMVP {
    var products: MutableLiveData<ArrayList<Product>> = MutableLiveData()
    var reviews:MutableLiveData<ArrayList<Review>> = MutableLiveData()
    private var productsDBModel = ProductsDBModel(this)

    constructor(){
        products.value = arrayListOf()
    }

    fun getAllProducts(storeID: String) {
        productsDBModel.getAllProducts(storeID)
    }

    override fun onAllProductsReady(products: ArrayList<Product>) {
        this.products.value = products
    }

    override fun onAllReviwsReady(reviews: ArrayList<Review>) {
       this.reviews.value=reviews
    }
    fun getReviewByProductId(productId: String){
        productsDBModel.getReviewByProductId(productId)
    }
    fun getProductById(productId: String){
        productsDBModel.getProductById(productId)
    }

}