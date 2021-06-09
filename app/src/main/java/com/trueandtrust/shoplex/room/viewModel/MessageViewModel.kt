package com.trueandtrust.shoplex.room.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trueandtrust.shoplex.model.pojo.Message
import com.trueandtrust.shoplex.room.data.ShoplexDatabase
import com.trueandtrust.shoplex.room.repository.MessageRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessageViewModel(context: Context,chatID : String) : ViewModel() {
    var readAllMessage : LiveData<List<Message>> = MutableLiveData()
    private val messageRepo : MessageRepo

    init {
        val messageDao = ShoplexDatabase.getDatabase(context).storeDao()
        messageRepo = MessageRepo(messageDao,chatID)
        readAllMessage = messageRepo.readAllMessage
    }

    fun addMessage(rightMessage: Message){
        viewModelScope.launch(Dispatchers.IO) {
            messageRepo.addMessage(rightMessage)
        }
    }

}