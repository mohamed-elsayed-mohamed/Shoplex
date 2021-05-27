package com.trueandtrust.shoplex.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trueandtrust.shoplex.model.firebase.OrdersDBModel
import com.trueandtrust.shoplex.model.interfaces.INotifyMVP
import com.trueandtrust.shoplex.model.pojo.Order
import com.trueandtrust.shoplex.model.pojo.Orders

class OrdersVM: ViewModel , INotifyMVP {

    private val ordersDBModel = OrdersDBModel(this)
    var order: MutableLiveData<ArrayList<Order>> = MutableLiveData()
    var lastOrders: MutableLiveData<ArrayList<Order>> = MutableLiveData()
    constructor(){

    }

    override fun onCurrentOrderReady(orders: ArrayList<Order>) {
        this.order.value = orders
    }
fun getCurrentOrders(){
ordersDBModel.getCurrentOrders()
}

    override fun onLastOrderReady(lastOrders: ArrayList<Order>) {
      this.lastOrders.value=lastOrders
    }
    fun getLastOrders(){
        ordersDBModel.getLastOrders()
    }
}