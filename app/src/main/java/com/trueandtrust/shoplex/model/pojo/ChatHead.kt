package com.trueandtrust.shoplex.model.pojo

data class ChatHead(val productId : String =" ",val storeId : String =" ",val chatId : String="",val productName:String="",val discountInfo : String="",val price: Double=0.0,val productImageURL : String ="",val userName : String="",val numOfMessage : Int=0) {

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
        return arrayListOf()
    }

}
