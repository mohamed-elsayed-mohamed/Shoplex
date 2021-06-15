package com.trueandtrust.shoplex.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trueandtrust.shoplex.model.firebase.ProductsDBModel
import com.trueandtrust.shoplex.model.interfaces.INotifyMVP
import com.trueandtrust.shoplex.model.pojo.Product
import com.trueandtrust.shoplex.model.pojo.Review
import com.trueandtrust.shoplex.model.pojo.ReviewStatistics

class ProductsVM: ViewModel, INotifyMVP {
    var products: MutableLiveData<ArrayList<Product>> = MutableLiveData()
    private var productsDBModel = ProductsDBModel(this)
    var reviews: MutableLiveData<ArrayList<Review>> = MutableLiveData()
    val reviewStatistics: MutableLiveData<ReviewStatistics> = MutableLiveData()

    constructor(){
        products.value = arrayListOf()
        reviewStatistics.value = ReviewStatistics()
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
        productsDBModel.getReviewsStatistics(productId)
        productsDBModel.getReviewByProductId(productId)
    }

    override fun onReviewStatisticsReady(reviewStatistics: ReviewStatistics) {
        this.reviewStatistics.value = reviewStatistics
    }

    fun getProductById(productId: String){
        productsDBModel.getProductById(productId)
    }

}