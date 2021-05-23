package com.trueandtrust.shoplex.model.pojo

import com.google.firebase.Timestamp
import java.util.*
import kotlin.collections.ArrayList

data class ChatHead(
    val productId: String =" ",
    val storeId: String =" ",
    val chatId: String="",
    val productName:String="",
    val discountInfo: String="",
    val price: Float=0.0F,
    val productImageURL: String? ="",
    val userID : String = " ",
    val userName: String="",
    val numOfMessage: Int=0,
    val date : Date = Timestamp.now().toDate()) {

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
