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

    constructor(
        productName: String,
        price: Double,
        productImageUrl: String,
        userName: String,
        numOfMessage: Int
    ) {
        this.productName = productName
        this.price = price
        this.productImageUrl = productImageUrl
        this.userName = userName
        this.numOfMessage = numOfMessage
    }

    constructor()

    fun getChatHeadsInfo() : ArrayList<ChatHead>{
        return arrayListOf(ChatHead())
    }

}
