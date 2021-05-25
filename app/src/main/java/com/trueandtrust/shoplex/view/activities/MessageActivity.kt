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
import com.trueandtrust.shoplex.model.pojo.StoreAccount
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder


class MessageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessageBinding
    val messageAdapter = GroupAdapter<GroupieViewHolder>()
    lateinit var chatID: String
    lateinit var userID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarMessage)
        supportActionBar?.apply {
            title = ""
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar()?.setDisplayShowHomeEnabled(true);
        }
        val userName = intent.getStringExtra(ChatHeadAdapter.CHAT_TITLE_KEY)
        val productImg = intent.getStringExtra(ChatHeadAdapter.CHAT_IMG_KEY)
        chatID = intent.getStringExtra(ChatHeadAdapter.CHAT_ID_KEY).toString()
        userID = intent.getStringExtra(ChatHeadAdapter.USER_ID_KEY).toString()

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
        val messageID = Timestamp.now().toDate().time.toString()
        val messageText = binding.edSendMesssage.text
        messageAdapter.add(RightMessageItem(Message(message = messageText.toString())))
        var message = Message(messageID, Timestamp.now().toDate(), userID, messageText.toString())
        FirebaseReferences.chatRef.document(chatID).collection("messages").document(messageID)
            .set(message)
        messageText.clear()

    }

    fun getAllMessage() {

        FirebaseReferences.chatRef.document(chatID).collection("messages").get()
            .addOnSuccessListener { result ->
                for (message in result) {
                    var msg: Message = message.toObject<Message>()
                    if (msg.toId.equals(StoreInfo.storeID)) {
                        messageAdapter.add(
                            LeftMessageItem(
                                Message(
                                    msg.messageID,
                                    msg.messageDate,
                                    msg.toId,
                                    msg.message
                                )
                            )
                        )
                    } else if (!msg.toId.equals(StoreInfo.storeID)) {

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
            R.id.sale -> Toast.makeText(this, "sale", Toast.LENGTH_SHORT).show()
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

}



