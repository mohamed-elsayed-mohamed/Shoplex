package com.trueandtrust.shoplex.model.adapter


import android.view.LayoutInflater
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ChatItemLeftBinding
import com.trueandtrust.shoplex.model.pojo.Message
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.chat_item_left.view.*
import java.util.*


class LeftMessageItem(val message: Message) : Item<GroupieViewHolder>() {
class LeftMessageItem(val message: Message) : Item<GroupieViewHolder>(){
    private lateinit var binding: ChatItemLeftBinding
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        binding = ChatItemLeftBinding.inflate(LayoutInflater.from(viewHolder.root.context))
        binding.tvReplyMessage.text = message.message
        binding.tvLeftDate.text = getDate(message.messageDate)
    }

    override fun getLayout(): Int {
        return R.layout.chat_item_left
    }


    fun getDate(date: Date): String {
        val sdf = java.text.SimpleDateFormat("dd-MM-yyyy hh:mm a")
        val result = sdf.format(date)
        return result
    }

}