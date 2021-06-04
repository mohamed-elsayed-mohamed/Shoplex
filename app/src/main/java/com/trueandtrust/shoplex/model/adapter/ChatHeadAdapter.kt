package com.trueandtrust.shoplex.model.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.toObject
import com.trueandtrust.shoplex.databinding.ChatHeadItemRowBinding
import com.trueandtrust.shoplex.model.extra.FirebaseReferences
import com.trueandtrust.shoplex.model.pojo.Chat
import com.trueandtrust.shoplex.model.pojo.ChatHead
import com.trueandtrust.shoplex.model.pojo.Product
import com.trueandtrust.shoplex.view.activities.MessageActivity


class ChatHeadAdapter(private val chatHeads: ArrayList<ChatHead>):
    RecyclerView.Adapter<ChatHeadAdapter.ChatHeadViewHolder>() {

    companion object {
        val CHAT_TITLE_KEY = "CHAT_TITLE_KEY"
        val CHAT_IMG_KEY = "CHAT_IMG_KEY"
        val CHAT_ID_KEY  = "CHAT_ID_KEY"
        val USER_ID_KEY = "USER_ID_KEY"
        val PRODUCTS_IDS = "PRODUCTS_IDS"
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ChatHeadViewHolder {
        return ChatHeadViewHolder(
            ChatHeadItemRowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        )
    }

    override fun onBindViewHolder(viewHolder: ChatHeadViewHolder, position: Int) {
        //viewHolder.bind(chatHeads[position])
        setListener(viewHolder, chatHeads[position])
    }

    override fun getItemCount() = chatHeads.size

    private fun setListener(viewHolder: ChatHeadViewHolder, chatHead: ChatHead){
        FirebaseReferences.chatRef.document(chatHead.chatId).addSnapshotListener { value, error ->
            if(error != null)
                return@addSnapshotListener

            if(value != null) {
                val chat: Chat = value.toObject()!!
                if(chat.productIDs.size != chatHead.productsIDs.size) {
                    FirebaseReferences.productsRef
                        .document(chat.productIDs.last()).get()
                        .addOnSuccessListener { productDocument ->
                            if (productDocument != null) {
                                val product = productDocument.toObject<Product>()!!
                                chatHead.productsIDs = chat.productIDs
                                chatHead.storeId = product.storeID
                                chatHead.productName = product.name
                                chatHead.price = product.price
                                chatHead.productImageURL = product.images[0]
                                chatHead.numOfMessage = chat.unreadStoreMessages
                            }
                        }
                }
                chatHead.numOfMessage = chat.unreadStoreMessages
                viewHolder.bind(chatHead)
            }
        }
    }

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
                intent.putExtra(PRODUCTS_IDS, chatHead.productsIDs)
                itemView.context.startActivity(intent)
            }
        }
    }

}
