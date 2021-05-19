package com.trueandtrust.shoplex.model.pojo


import android.os.Parcel
import com.trueandtrust.shoplex.model.enumurations.OrderStatus

class Orders : Product {
    constructor(
        name: String,
        price: Float,
        category: String,
        orderStatus:OrderStatus,
        productImageUrl: String,

        ):super(){

    }
    constructor()
    var orderID: Int = 0
    var orderStatus: OrderStatus = OrderStatus.CURRENT
    fun getOrders(storeId: Int): ArrayList<Orders> {

        return arrayListOf(Orders())
    }

}
//class Orders(
//    override var name: String,
//    override var price: Float,
//    override var category: String,
//    var orderStatus: OrderStatus,
//    override var productImageUrl: String
//
//) : Product() {
//
//    constructor()
//    var orderID : Int = 0
//
//
//    fun getOrders(storeId : Int) : ArrayList<Orders>{
//
//        return arrayListOf(Orders())
//    }
//
//}