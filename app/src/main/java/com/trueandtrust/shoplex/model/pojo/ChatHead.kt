package com.trueandtrust.shoplex.model.pojo

data class ChatHead(val productId : String =" ",val storeId : String =" ",val chatId : String="",val productName:String="",val discountInfo : String="",val price: Double=0.0,val productImageURL : String ="",val userName : String="",val numOfMessage : Int=0) {

    fun getChatHeadsInfo() : ArrayList<ChatHead>{
        return arrayListOf()
    }

}
