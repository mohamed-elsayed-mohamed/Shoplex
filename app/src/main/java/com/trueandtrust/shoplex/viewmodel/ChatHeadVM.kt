package com.trueandtrust.shoplex.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trueandtrust.shoplex.model.firebase.ChatHeadDBModel
import com.trueandtrust.shoplex.model.firebase.OrdersDBModel
import com.trueandtrust.shoplex.model.interfaces.INotifyMVP
import com.trueandtrust.shoplex.model.pojo.ChatHead
import com.trueandtrust.shoplex.model.pojo.Order

class ChatHeadVM: ViewModel, INotifyMVP {

    private val chatHeadDBModel = ChatHeadDBModel(this)
    var chatHead: MutableLiveData<ArrayList<ChatHead>> = MutableLiveData()

    constructor(){

    }

    override fun ongetChatHead(chatHeads: ArrayList<ChatHead>) {
        this.chatHead.value = chatHeads
    }
    fun getChatHead(){
        chatHeadDBModel.getChatHead()
    }

}