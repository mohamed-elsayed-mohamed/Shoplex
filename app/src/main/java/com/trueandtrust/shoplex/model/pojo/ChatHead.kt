package com.trueandtrust.shoplex.model.pojo

class ChatHead {

    var productID : Int = 0
    var storeID : Int = 0
    var chatID : Int = 0
    var productName : String  = ""
    var discountInfo : String = ""
    var price : Double  = 0.0
    var productImageUrl : String = ""
    var userName : String = ""
    var numOfMessage : Int = 0

    fun getChatHeadsInfo() : ArrayList<ChatHead>{
        return arrayListOf(ChatHead())
    }

}
