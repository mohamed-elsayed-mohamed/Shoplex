package com.trueandtrust.shoplex.model.pojo

import com.trueandtrust.shoplex.model.enumurations.OrderStatus

class Orders : Product() {
    var orderID : Int = 0
    var orderStatus : OrderStatus = OrderStatus.CURRENT

    fun getOrders(storeId : Int) : ArrayList<Orders>{

        return arrayListOf(Orders())
    }

}