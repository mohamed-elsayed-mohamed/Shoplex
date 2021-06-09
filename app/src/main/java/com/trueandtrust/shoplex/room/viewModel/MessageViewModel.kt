package com.trueandtrust.shoplex.room.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trueandtrust.shoplex.model.pojo.Message
import com.trueandtrust.shoplex.room.data.ShoplexDatabase
import com.trueandtrust.shoplex.room.repository.MessageRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessageViewModel(context: Context,chatID : String) : ViewModel() {
    val readAllMessage: LiveData<List<Message>>
    private val messageRepo: MessageRepo

    init {
        val messageDao = ShoplexDatabase.getDatabase(context).storeDao()
        messageRepo = MessageRepo(messageDao, chatID)
        readAllMessage = messageRepo.readAllMessage
    }

    fun addRightMessage(rightMessage: Message) {
        viewModelScope.launch(Dispatchers.IO) {
            messageRepo.addRightMessage(rightMessage)
        }
    }

    fun addLeftMessage(leftMessage: Message) {
        viewModelScope.launch(Dispatchers.IO) {
            messageRepo.addLeftMessage(leftMessage)
        }
    }
}