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

    fun getChatHeadsInfo() : ArrayList<ChatHead>{
        return arrayListOf()
    }

}
