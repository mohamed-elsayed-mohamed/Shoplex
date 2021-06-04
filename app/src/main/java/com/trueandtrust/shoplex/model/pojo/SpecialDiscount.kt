package com.trueandtrust.shoplex.model.pojo

import com.shoplex.shoplex.model.enumurations.DiscountType

class SpecialDiscount {
    var storeID = ""
    var productID = ""
    var userID = ""
    var discount: Float = 0F
    var discountType: DiscountType = DiscountType.Fixed

    private constructor()
    constructor(discount: Float, discountType: DiscountType){
        this.discount = discount
        this.discountType = discountType
    }

    constructor(
        storeID: String,
        productID: String,
        userID: String,
        discount: Float,
        discountType: DiscountType
    ) {
        this.storeID = storeID
        this.productID = productID
        this.userID = userID
        this.discount = discount
        this.discountType = discountType
    }
}