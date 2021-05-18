package com.trueandtrust.shoplex.view.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ActivityMessageBinding
import com.trueandtrust.shoplex.model.adapter.ChatHeadAdapter
import com.trueandtrust.shoplex.model.adapter.LeftMessageItem
import com.trueandtrust.shoplex.model.adapter.RightMessageItem
import com.trueandtrust.shoplex.model.pojo.Message
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class MessageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessageBinding
    private var messageList = arrayListOf<Message>()
    private var message: Message = Message()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarMessage)
        supportActionBar?.setTitle("")

        val userName = intent.getStringExtra(ChatHeadAdapter.CHAT_TITLE_KEY)

        binding.imgToolbarChat.setImageResource(R.drawable.product)
        binding.tvToolbarUserChat.text = userName


        val messageAdapter = GroupAdapter<GroupieViewHolder>()

        messageAdapter.add(LeftMessageItem(Message(toId = "1", message = "hello")))
        messageAdapter.add(LeftMessageItem(Message(toId = "1", message = "hello")))
        messageAdapter.add(RightMessageItem(Message(toId = "0", message = "send hello")))
        messageAdapter.add(LeftMessageItem(Message(toId = "1", message = "hello")))

        binding.rcMessage.adapter = messageAdapter

        binding.btnSendMessage.setOnClickListener{
            performSendMessage()
        }

    }

    private fun performSendMessage() {
       //send Message to Firebase
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.message_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sale -> Toast.makeText(this, "sale", Toast.LENGTH_SHORT).show()
        }
        return false
    }
}



