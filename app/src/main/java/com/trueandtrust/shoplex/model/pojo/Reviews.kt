package com.trueandtrust.shoplex.model.pojo

import com.trueandtrust.shoplex.model.pojo.Review

class Reviews {
    var totalRate : Double = 0.0
    var rateNumber : Int = 0


    fun getAllReviews(produtID : Int) : ArrayList<Review>{

        return arrayListOf(Review())
    }

   fun getStatistics() : ArrayList<Int>? {
        return null
    }
}