package com.trueandtrust.shoplex.model.pojo

import com.google.firebase.firestore.Exclude

data class Chat(var chatID : String = "", val userID: String = "",val userName:String = "", val storeID: String = "", var productIDs: ArrayList<String> = arrayListOf(String()), val unreadStoreMessages: Int = 0, @Exclude @set:Exclude var unreadCustomerMessages: Int = 0)