package com.trueandtrust.shoplex.model.pojo

data class Chat(val chatID : String, val userID: String, val storeID: String, var productIDs: ArrayList<String>) {

    fun getAllMessages(chatID : Int) : ArrayList<Message>{

        return  arrayListOf()
    }

    fun getMessages(message: Message,chatID : Int) : Boolean{
        return true
    }
}