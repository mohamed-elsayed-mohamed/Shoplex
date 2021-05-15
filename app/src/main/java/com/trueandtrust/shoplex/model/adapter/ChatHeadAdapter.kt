package com.trueandtrust.shoplex.model.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.model.pojo.ChatHead
import kotlinx.coroutines.withContext


class ChatHeadAdapter (private val chatHead: ArrayList<ChatHead>) :
    RecyclerView.Adapter<ChatHeadAdapter.ViewHolder>() {

   // var chatHead = listOf<ChatHead>()
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image : ImageView = view.findViewById(R.id.imgChatHead)
        val userName : TextView = view.findViewById(R.id.tvUserNameChatHead)
        val productName : TextView = view.findViewById(R.id.tvProductNameChatHead)
        val numOfMessage :TextView = view.findViewById(R.id.tvNumOfMessage)
        val price : TextView = view.findViewById(R.id.tvPriceChatHeader)

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.chat_head_item_row, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = chatHead[position]
        Glide.with(viewHolder.itemView.context).load(item.productImageUrl).into(viewHolder.image)

        //viewHolder.image.setImageResource(item.productImageUrl.toInt())
        viewHolder.userName.text = item.userName
        viewHolder.productName.text = item.productName
        viewHolder.numOfMessage.text = item.numOfMessage.toString()
        viewHolder.price.text = item.price.toString()

    }

    override fun getItemCount() = chatHead.size


}