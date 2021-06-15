package com.trueandtrust.shoplex.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trueandtrust.shoplex.model.firebase.OrdersDBModel
import com.trueandtrust.shoplex.model.interfaces.OrdersListener
import com.trueandtrust.shoplex.model.pojo.Order

class OrdersVM : ViewModel(), OrdersListener {

    private val ordersDBModel = OrdersDBModel(this)
    var order: MutableLiveData<ArrayList<Order>> = MutableLiveData()
    var lastOrders: MutableLiveData<ArrayList<Order>> = MutableLiveData()

    fun getCurrentOrders() {
        ordersDBModel.getCurrentOrders()
    }

    fun getLastOrders() {
        ordersDBModel.getLastOrders()
    }

    override fun onCurrentOrderReady(orders: ArrayList<Order>) {
        this.order.value = orders
    }

    override fun onLastOrderReady(lastOrders: ArrayList<Order>) {
        this.lastOrders.value = lastOrders
    }
}