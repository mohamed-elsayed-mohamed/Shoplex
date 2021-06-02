package com.trueandtrust.shoplex.model.pojo


import android.os.Parcel
import com.google.firebase.firestore.Exclude
import com.shoplex.shoplex.model.enumurations.OrderStatus


class Orders : Product {
    @Exclude
    @set:Exclude @get:Exclude
    var product:Product? = null
    constructor(
        name: String,
        price: Float,
        category: String,
        orderStatus: OrderStatus,
        productImageUrl: String,

        ) : super() {

    }

    constructor()

    var orderID: Int = 0
    var orderStatus: OrderStatus = OrderStatus.Current
    fun getOrders(storeId: Int): ArrayList<Orders> {

        return arrayListOf(Orders())
    }
}