package com.trueandtrust.shoplex.model.pojo

import com.trueandtrust.shoplex.model.enumurations.DiscountType

class SpecialDiscount {
    var discount: Float = 0F
    var discountType: DiscountType = DiscountType.Fixed

    private constructor()
    constructor(discount: Float, discountType: DiscountType){
        this.discount = discount
        this.discountType = discountType
    }
}