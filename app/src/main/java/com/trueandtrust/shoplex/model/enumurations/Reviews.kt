package com.trueandtrust.shoplex.model.enumurations

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