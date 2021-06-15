package com.trueandtrust.shoplex.model.firebase

import com.trueandtrust.shoplex.model.extra.StoreInfo
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.toObject
import com.shoplex.shoplex.model.enumurations.OrderStatus
import com.trueandtrust.shoplex.model.extra.FirebaseReferences
import com.trueandtrust.shoplex.model.interfaces.INotifyMVP
import com.trueandtrust.shoplex.model.interfaces.OrdersListener
import com.trueandtrust.shoplex.model.pojo.Order
import com.trueandtrust.shoplex.model.pojo.Product

class OrdersDBModel (val listener: OrdersListener) {

    fun getCurrentOrders() {
        FirebaseReferences.ordersRef.whereEqualTo("storeID", StoreInfo.storeID)
            .whereEqualTo("orderStatus", OrderStatus.Current.name)
            .addSnapshotListener { values, _ ->
                var orders = arrayListOf<Order>()
                for (document: DocumentSnapshot in values?.documents!!) {
                    var order: Order? = document.toObject<Order>()
                    if (order != null) {
                        orders.add(order)
                        FirebaseReferences.productsRef.document(order.productID).get()
                            .addOnSuccessListener {
                                order.product = it.toObject<Product>()
                                if (document == values.last()) {
                                    this.listener.onCurrentOrderReady(orders)
                                }
                            }
                    }
                }

            }
    }

    fun getLastOrders() {
        FirebaseReferences.ordersRef.whereEqualTo("storeID", StoreInfo.storeID).whereIn(
            "orderStatus",
            listOf(OrderStatus.Delivered.name, OrderStatus.Canceled.name)
        )
            .addSnapshotListener { values, _ ->
                var orders = arrayListOf<Order>()
                for (document: DocumentSnapshot in values?.documents!!) {
                    var order: Order? = document.toObject<Order>()
                    if (order != null) {
                        orders.add(order)
                        FirebaseReferences.productsRef.document(order.productID).get()
                            .addOnSuccessListener {
                                order.product = it.toObject<Product>()
                                if (document == values.last()) {
                                    this.listener.onLastOrderReady(orders)
                                }
                            }
                    }
                }
            }
    }

    companion object{
        fun deliverOrder(orderID: String){
            FirebaseReferences.ordersRef.document(orderID)
                .update("orderStatus", OrderStatus.Delivered)
        }
    }
}