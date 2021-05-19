package com.trueandtrust.shoplex.model.adapter

import android.view.LayoutInflater
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ChatItemRightBinding
import com.trueandtrust.shoplex.model.pojo.Message
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import java.util.*


class RightMessageItem(val message: Message) : Item<GroupieViewHolder>(){
    private lateinit var binding: ChatItemRightBinding
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        binding = ChatItemRightBinding.inflate(LayoutInflater.from(viewHolder.root.context))
        binding.tvSendMessage.text = message.message
        binding.tvRightDate.text = getDate(message.messageDate)
    }
    override fun getLayout(): Int {
        return R.layout.chat_item_right
    }

    fun getDate(date: Date): String {
        val sdf = java.text.SimpleDateFormat("dd-MM-yyyy hh:mm a")
        val result = sdf.format(date)
        return result
    }

}