package com.trueandtrust.shoplex.model.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ChatHeadItemRowBinding
import com.trueandtrust.shoplex.databinding.ProductGvBinding
import com.trueandtrust.shoplex.model.pojo.ChatHead
import com.trueandtrust.shoplex.model.pojo.Product
import com.trueandtrust.shoplex.view.activities.MessageActivity


class ChatHeadAdapter(val chatHead: ArrayList<ChatHead>) :
    RecyclerView.Adapter<ChatHeadAdapter.ChatHeadViewHolder>() {

    companion object{
        val CHAT_TITLE_KEY = "CHAT_TITLE_KEY"
    }

    inner class ChatHeadViewHolder(val binding: ChatHeadItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(chatHead: ChatHead) {
            Glide.with(itemView.context).load(chatHead.productImageURL).into(binding.imgChatHead)
            //viewHolder.image.setImageResource(item.productImageUrl.toInt())
            binding.tvUserNameChatHead.text = chatHead.userName
            binding.tvProductNameChatHead.text = chatHead.productName
            binding.tvNumOfMessage.text = chatHead.numOfMessage.toString()
            binding.tvPriceChatHeader.text = chatHead.price.toString()
            itemView.setOnClickListener {
                val intent = Intent(itemView.context,MessageActivity::class.java)
                intent.putExtra(CHAT_TITLE_KEY,chatHead.userName)
                itemView.context.startActivity(intent)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ChatHeadViewHolder {
        // Create a new view, which defines the UI of the list item
        return ChatHeadViewHolder(
            ChatHeadItemRowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        )
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ChatHeadViewHolder, position: Int) {
        viewHolder.bind(chatHead[position])
    }

    override fun getItemCount() = chatHead.size

}