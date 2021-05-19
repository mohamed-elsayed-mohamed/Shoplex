package com.trueandtrust.shoplex.model.pojo

import com.trueandtrust.shoplex.model.enumurations.OrderStatus

data class Orders(var orderID :String="", var orderStatus : OrderStatus = OrderStatus.CURRENT,override var name: String="",
                  override var price: Float = 0.0F, override var category: String="", var productImageUrl: String="") : Product() {



    fun getOrders(storeId : Int) : ArrayList<Orders>{

        return arrayListOf(Orders())
    }

}