package com.trueandtrust.shoplex.model.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ChatItemLeftBinding
import com.trueandtrust.shoplex.databinding.ChatItemRightBinding
import com.trueandtrust.shoplex.model.pojo.Message
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.chat_item_left.view.*
import kotlinx.android.synthetic.main.chat_item_right.view.*
import java.util.*


class RightMessageItem(val message: Message) : Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.tvSendMessage.text = message.message
        viewHolder.itemView.tvRightDate.text = getDate(message.messageDate)
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