package com.trueandtrust.shoplex.model.firebase
import com.trueandtrust.shoplex.model.extra.StoreInfo


import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.toObject
import com.trueandtrust.shoplex.model.extra.FirebaseReferences
import com.trueandtrust.shoplex.model.extra.StoreInfo.storeID
import com.trueandtrust.shoplex.model.interfaces.INotifyMVP
import com.trueandtrust.shoplex.model.pojo.Order
import com.trueandtrust.shoplex.model.pojo.Orders
import com.trueandtrust.shoplex.model.pojo.Product

class OrdersDBModel (val notifier : INotifyMVP){

    fun getCurrentOrders() {

        FirebaseReferences.ordersRef.whereEqualTo("storeID",StoreInfo.storeID).whereEqualTo("orderStatus","Current")
            .addSnapshotListener { values, _ ->
                var orders = arrayListOf<Order>()
                for (document: DocumentSnapshot in values?.documents!!) {
                    var order: Order? = document.toObject<Order>()
                    if (order != null) {
                        orders.add(order)
                        FirebaseReferences.productsRef.document(order.productID).get().addOnSuccessListener {
                            order.product=it.toObject<Product>()
                            if (document==values.last()){
                                this.notifier?.onCurrentOrderReady(orders)
                            }
                        }
                    }
                }

            }
    }
}