package com.trueandtrust.shoplex.view.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.Timestamp
import com.google.firebase.auth.UserInfo
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ActivityMessageBinding
import com.trueandtrust.shoplex.model.adapter.ChatHeadAdapter
import com.trueandtrust.shoplex.model.adapter.LeftMessageItem
import com.trueandtrust.shoplex.model.adapter.RightMessageItem
import com.trueandtrust.shoplex.model.extra.FirebaseReferences
import com.trueandtrust.shoplex.model.extra.StoreInfo
import com.trueandtrust.shoplex.model.pojo.Message
import com.trueandtrust.shoplex.model.pojo.Product
import com.trueandtrust.shoplex.model.pojo.StoreAccount
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder


class MessageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessageBinding
    val messageAdapter = GroupAdapter<GroupieViewHolder>()
    lateinit var chatID: String
    lateinit var userID: String
    private var firstUnread: Int = -1
    private var productsIDs: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarMessage)
        supportActionBar?.apply {
            title = ""
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true);
            supportActionBar!!.setDisplayShowHomeEnabled(true);
        }
        val userName = intent.getStringExtra(ChatHeadAdapter.CHAT_TITLE_KEY)
        val productImg = intent.getStringExtra(ChatHeadAdapter.CHAT_IMG_KEY)
        chatID = intent.getStringExtra(ChatHeadAdapter.CHAT_ID_KEY).toString()
        userID = intent.getStringExtra(ChatHeadAdapter.USER_ID_KEY).toString()
        productsIDs = intent.getStringArrayListExtra(ChatHeadAdapter.PRODUCTS_IDS)

        binding.imgToolbarChat.setImageResource(R.drawable.product)
        binding.tvToolbarUserChat.text = userName
        Glide.with(this).load(productImg).into(binding.imgToolbarChat)

        getAllMessage()

        binding.btnSendMessage.setOnClickListener {

            performSendMessage()
        }
    }

    private fun performSendMessage() {
        //send Message to Firebase
        val messageText = binding.edSendMesssage.text
        messageAdapter.add(RightMessageItem(Message(message = messageText.toString())))
        var message = Message(toId = userID, message = messageText.toString())
        FirebaseReferences.chatRef.document(chatID).collection("messages").document(message.messageID)
            .set(message)
        messageText.clear()
    }

    private fun getAllMessage() {
        FirebaseReferences.chatRef.document(chatID).collection("messages").get()
            .addOnSuccessListener { result ->
                for ((index, message) in result.withIndex()) {
                    var msg: Message = message.toObject<Message>()
                    if (msg.toId == StoreInfo.storeID) {
                        messageAdapter.add(
                            LeftMessageItem(
                                chatID,
                                Message(
                                    msg.messageID,
                                    msg.messageDate,
                                    msg.toId,
                                    msg.message,
                                    msg.isSent,
                                    msg.isRead
                                )
                            )
                        )

                        if(!msg.isSent){
                            FirebaseReferences.chatRef.document(chatID).collection("messages").document(msg.messageID).update("isSent", true)
                            if (firstUnread == -1)
                                firstUnread = index
                        }
                    } else if (msg.toId != StoreInfo.storeID) {

                        messageAdapter.add(
                            RightMessageItem(
                                Message(
                                    msg.messageID,
                                    msg.messageDate,
                                    msg.toId,
                                    msg.message
                                )
                            )
                        )

                    }
                    if (firstUnread != -1)
                        binding.rcMessage.scrollToPosition(firstUnread)
                    else
                        binding.rcMessage.scrollToPosition(result.size() -1);
                }

                binding.rcMessage.adapter = messageAdapter
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.message_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sale -> {
                if(productsIDs !=null) {
                    FirebaseReferences.productsRef.whereIn("productID", productsIDs!!).get().addOnCompleteListener {
                    var products: ArrayList<Product> = arrayListOf()
                        for (product in it.result){
                            products.add(product.toObject())
                            if(product == it.result.last()){
                                Toast.makeText(this, products.last().productID, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                // Toast.makeText(this, getString(R.string.sale) + chatID, Toast.LENGTH_SHORT).show()
            }
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

}



