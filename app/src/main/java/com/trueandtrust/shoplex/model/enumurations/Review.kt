package com.trueandtrust.shoplex.model.enumurations

import java.util.*

class Review {

    var productID : Int = 0
    var customerName : String = ""
    var customerImageUrl : String = ""
    var comment : String = ""
    lateinit var date : Date
    var rate : Double = 0.0
}