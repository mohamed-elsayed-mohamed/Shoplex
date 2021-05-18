package com.trueandtrust.shoplex.model.adapter


import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.model.pojo.Message
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.chat_item_left.view.*


class LeftMessageItem(val message: Message) : Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.tvReplyMessage.text = message.message
        viewHolder.itemView.tvLeftDate.text = message.messageDate.toString()
    }

    override fun getLayout(): Int {
        return R.layout.chat_item_left
    }

}