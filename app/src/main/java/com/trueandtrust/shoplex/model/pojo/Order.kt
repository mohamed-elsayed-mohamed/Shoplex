package com.trueandtrust.shoplex.model.pojo

import com.google.firebase.firestore.Exclude
import com.shoplex.shoplex.model.enumurations.DeliveryMethod
import com.shoplex.shoplex.model.enumurations.OrderStatus
import com.shoplex.shoplex.model.enumurations.PaymentMethod

class Order {
    var orderID: String? = null
    var productID: String = ""
    var userID: String = ""
    var storeID: String = ""
    var orderStatus: OrderStatus = OrderStatus.Current
    var quantity: Int = 1
    var specialDiscount: SpecialDiscount? = null
    var deliveryLocation: Location? = null
    var deliveryMethod: DeliveryMethod? = null
    var paymentMethod: PaymentMethod? = null
    var subTotalPrice: Float = 0F
    var totalDiscount: Float = 0F
    var shipping: Float = 0F
    var totalPrice: Float = 0F
    @Exclude
    @set:Exclude @get:Exclude
    var product:Product? = null

    constructor()
}