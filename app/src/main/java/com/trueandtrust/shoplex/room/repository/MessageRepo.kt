package com.trueandtrust.shoplex.room.repository

import androidx.lifecycle.LiveData
import com.trueandtrust.shoplex.model.extra.StoreInfo
import com.trueandtrust.shoplex.model.pojo.Message
import com.trueandtrust.shoplex.room.data.StoreDao

class MessageRepo(private val messageDao: StoreDao ,val chatID : String) {

    val readAllMessage : LiveData<List<Message>> = messageDao.readAllMessage(chatID)

    suspend fun addMessage(rightMessage : Message){
        messageDao.addMessage(rightMessage)
    }
}