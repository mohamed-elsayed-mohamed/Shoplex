package com.trueandtrust.shoplex.model.pojo

class Chat {

    var chatID : Int = 0
    lateinit var chatHead : ChatHead
    lateinit var  message : Message

    fun getAllMessages(chatID : Int) : ArrayList<Message>{

        return  arrayListOf(Message())
    }

    fun getMessages(message: Message,chatID : Int) : Boolean{
        return true
    }
}