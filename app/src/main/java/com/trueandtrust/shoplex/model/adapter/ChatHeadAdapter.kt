package com.trueandtrust.shoplex.model.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ChatHeadItemRowBinding
import com.trueandtrust.shoplex.model.adapter.ChatHeadAdapter.Companion.CHAT_TITLE_KEY
import com.trueandtrust.shoplex.model.pojo.ChatHead
import com.trueandtrust.shoplex.view.activities.MessageActivity


class ChatHeadAdapter(private val chatHead: ArrayList<ChatHead>) :
    RecyclerView.Adapter<ChatHeadAdapter.ChatHeadViewHolder>() {

    companion object {
        val CHAT_TITLE_KEY = R.string.CHAT_TITLE_KEY.toString()
        val CHAT_IMG_KEY = "CHAT_IMG_KEY"
        val CHAT_ID_KEY  = "CHAT_ID_KEY"
        val USER_ID_KEY = "USER_ID_KEY"
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ChatHeadViewHolder {
        return ChatHeadViewHolder(
            ChatHeadItemRowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        )
    }

    override fun onBindViewHolder(viewHolder: ChatHeadViewHolder, position: Int) {
        viewHolder.bind(chatHead[position])
    }

    override fun getItemCount() = chatHead.size

    inner class ChatHeadViewHolder(val binding: ChatHeadItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(chatHead: ChatHead) {
            Glide.with(itemView.context).load(chatHead.productImageURL).into(binding.imgChatHead)
            binding.tvUserNameChatHead.text = chatHead.userName
            binding.tvProductNameChatHead.text = chatHead.productName
            binding.tvNumOfMessage.text = chatHead.numOfMessage.toString()
            binding.tvPriceChatHeader.text = chatHead.price.toString()
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, MessageActivity::class.java)
                intent.putExtra(CHAT_TITLE_KEY, chatHead.userName)
                intent.putExtra(CHAT_IMG_KEY,chatHead.productImageURL)
                intent.putExtra(CHAT_ID_KEY,chatHead.chatId)
                intent.putExtra(USER_ID_KEY,chatHead.userID)
                itemView.context.startActivity(intent)
            }
        }
    }

}
