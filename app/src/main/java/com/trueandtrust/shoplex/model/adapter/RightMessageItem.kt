package com.trueandtrust.shoplex.model.adapter

import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ChatItemRightBinding
import com.trueandtrust.shoplex.model.pojo.Message
import com.xwray.groupie.databinding.BindableItem


class RightMessageItem(val message: Message) : BindableItem<ChatItemRightBinding>(){

    override fun bind(binding: ChatItemRightBinding, position: Int) {
        binding.message = message
    }

    override fun getLayout(): Int {
        return R.layout.chat_item_right
    }
}

/*  fun getDate(date: Date): String {
        val sdf = java.text.SimpleDateFormat("dd-MM-yyyy hh:mm a")
        val result = sdf.format(date)
        return result
    }*/