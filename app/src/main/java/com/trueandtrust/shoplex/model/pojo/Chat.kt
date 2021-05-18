package com.trueandtrust.shoplex.model.pojo

class Chat {

    var chatID : Int = 0
    lateinit var chatHead : ChatHead
    lateinit var  message : ArrayList<Message>

    fun getAllMessages(chatID : Int) : ArrayList<Message>{

        return  arrayListOf()
    }

    fun getMessages(message: Message,chatID : Int) : Boolean{
        return true
    }
}