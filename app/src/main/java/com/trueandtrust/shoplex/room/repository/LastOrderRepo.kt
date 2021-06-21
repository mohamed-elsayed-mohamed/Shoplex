package com.trueandtrust.shoplex.room.repository

import androidx.lifecycle.LiveData
import com.trueandtrust.shoplex.model.enumurations.DeliveryMethod
import com.trueandtrust.shoplex.model.enumurations.OrderStatus
import com.trueandtrust.shoplex.model.pojo.Message
import com.trueandtrust.shoplex.model.pojo.Order
import com.trueandtrust.shoplex.room.data.StoreDao

class LastOrderRepo(private val lastOrderDao : StoreDao, val orderDelivered : OrderStatus, val orderCanceled: OrderStatus ) {

    val readAllLastOrder : LiveData<List<Order>> = lastOrderDao.readAllLastOrder(orderDelivered,orderCanceled)

    suspend fun addLastOrder(lastOrder : Order){
       lastOrderDao.addLasOrder(lastOrder)
    }
}
