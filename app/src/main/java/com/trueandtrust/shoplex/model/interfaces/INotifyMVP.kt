package com.trueandtrust.shoplex.model.interfaces

import com.trueandtrust.shoplex.model.pojo.*
import kotlin.collections.ArrayList

interface INotifyMVP {
    fun onImageRemoved(position: Int){}
    fun onImageUploadedSuccess(path: String, position: Int){}
    fun onImageUploadedFailed(position: Int){}
    fun onUploadSuccess(){}
    fun onUploadFailed(){}
    fun onNewPropertyAdded(property: Property){}
    fun onPropertyRemoved(position: Int){}
    fun onAllProductsReady(products: ArrayList<Product>){}
    fun onProductRemoved(){}

    fun onStoreInfoReady(isAccountActive: Boolean){}
    fun onStoreInfoFailed(){}
    fun onCurrentOrderReady(orders:ArrayList<Order>){}
    fun onLastOrderReady(lastOrders:ArrayList<Order>){}
    fun onAllReviwsReady(reviews:ArrayList<Review>){}
    fun onReviewStatisticsReady(reviewStatistics: ReviewStatistics){}
    fun ongetChatHead(chatHeads: ArrayList<ChatHead>){}
}