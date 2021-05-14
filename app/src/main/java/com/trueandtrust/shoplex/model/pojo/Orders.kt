package com.trueandtrust.shoplex.model.pojo

import com.google.android.gms.maps.model.LatLng
import com.trueandtrust.shoplex.model.enumurations.Category
import com.trueandtrust.shoplex.model.enumurations.OrderStatus

class Orders(
    override var name: String,
    override var price: Float,
    override var category: Category,
    var orderStatus: OrderStatus,
    override var productImageUrl: String
) : Product() {

    class Orders : Product()
    var orderID : Int = 0


    fun getOrders(storeId : Int) : ArrayList<Orders>{

        return arrayListOf(Orders())
    }

}