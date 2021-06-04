package com.trueandtrust.shoplex.model.pojo

import com.google.firebase.Timestamp
import java.util.*
import kotlin.collections.ArrayList

data class ChatHead(
    var productsIDs: ArrayList<String> = arrayListOf(),
    var storeId: String =" ",
    val chatId: String="",
    var productName:String="",
    var price: Float=0.0F,
    var productImageURL: String? ="",
    val userID : String = " ",
    val userName: String="",
    var numOfMessage: Int=0,
    val date : Date = Timestamp.now().toDate())
