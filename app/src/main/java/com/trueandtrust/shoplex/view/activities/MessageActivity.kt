package com.trueandtrust.shoplex.view.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.toObject
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ActivityMessageBinding
import com.trueandtrust.shoplex.databinding.DialogAddSaleBinding
import com.trueandtrust.shoplex.model.adapter.ChatHeadAdapter
import com.trueandtrust.shoplex.model.adapter.LeftMessageItem
import com.trueandtrust.shoplex.model.adapter.RightMessageItem
import com.trueandtrust.shoplex.model.adapter.SalesAdapter
import com.trueandtrust.shoplex.model.extra.FirebaseReferences
import com.trueandtrust.shoplex.model.extra.StoreInfo
import com.trueandtrust.shoplex.model.pojo.Message
import com.trueandtrust.shoplex.model.pojo.Product
import com.trueandtrust.shoplex.room.viewModel.MessageFactoryModel
import com.trueandtrust.shoplex.room.viewModel.MessageViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder


class MessageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessageBinding
    val messageAdapter = GroupAdapter<GroupieViewHolder>()
    lateinit var chatID: String
    lateinit var userID: String
    private var firstUnread: Int = -1
    private var productsIDs: ArrayList<String>? = null
    private lateinit var messageVM: MessageViewModel

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

        messageVM = ViewModelProvider(this,MessageFactoryModel(this,chatID)).get(MessageViewModel::class.java)

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
        FirebaseReferences.chatRef.document(chatID).collection("messages")
            .document(message.messageID)
            .set(message)

        messageText.clear()
    }

    private fun listenToNewMessages(lastID: String){
        FirebaseReferences.chatRef.document(chatID).collection("messages").whereGreaterThan("messageID",lastID).addSnapshotListener{snapshots,error ->
           if(error != null){
               return@addSnapshotListener
           }
            for (dc in snapshots!!.documentChanges) {
                if (dc.type ==   DocumentChange.Type.ADDED) {
                  val message = dc.document.toObject<Message>()
                    if (message.toId == StoreInfo.storeID) {
                        //messageAdapter.add(LeftMessageItem(chatID, message))
                        messageVM.addMessage(message)

                    } else if (message.toId != StoreInfo.storeID) {
                        messageVM.addMessage(message)
                    }
                }
            }

        }
    }

    private fun getAllMessage() {
        messageVM.readAllMessage.observe(this, Observer {
            for (message in it) {
                if (message.toId == StoreInfo.storeID) {
                    messageAdapter.add(LeftMessageItem(chatID, message))

                } else if (message.toId != StoreInfo.storeID) {
                    messageAdapter.add(RightMessageItem(message))
                }
            }
            val lastID = if(it.isEmpty()){
                "1"
            }else{
                it.last().messageID
            }
            getAllMessageFromFirebase(lastID)

        })
    }

    private fun getAllMessageFromFirebase(lastID: String) {
        FirebaseReferences.chatRef.document(chatID).collection("messages")
            .whereGreaterThan("messageID", lastID).get()
            .addOnSuccessListener { result ->
                for ((index, message) in result.withIndex()) {
                    var msg: Message = message.toObject<Message>()
                    if (msg.toId == StoreInfo.storeID) {
                        var message = Message(msg.messageID,msg.messageDate, msg.toId, msg.message, msg.isSent, msg.isRead)
                        messageAdapter.add(LeftMessageItem(chatID, message))
                        messageVM.addMessage(message)
                        if (!msg.isSent) {
                            FirebaseReferences.chatRef.document(chatID).collection("messages")
                                .document(msg.messageID).update("isSent", true)
                            if (firstUnread == -1)
                                firstUnread = index
                        }
                    } else if (msg.toId != StoreInfo.storeID) {
                        var message = Message(msg.messageID, msg.messageDate, msg.toId, msg.message)
                        messageAdapter.add(RightMessageItem(message))
                        messageVM.addMessage(message)
                    }
                    if (firstUnread != -1)
                        binding.rcMessage.scrollToPosition(firstUnread)
                    else
                        binding.rcMessage.scrollToPosition(result.size() - 1);
                }
                listenToNewMessages(lastID)
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
                if (productsIDs != null) {
                    FirebaseReferences.productsRef.whereIn("productID", productsIDs!!).get()
                        .addOnCompleteListener {
                            var products: ArrayList<Product> = arrayListOf()
                            for (product in it.result) {
                                products.add(product.toObject())
                                if (product == it.result.last()) {
                                    openSnackBar(products)
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

    private fun openSnackBar(products: ArrayList<Product>) {
        Snackbar.make(binding.root, getString(R.string.sale), Snackbar.LENGTH_LONG).show()
        val binding = DialogAddSaleBinding.inflate(layoutInflater)
        val SalesBtnSheetDialog = BottomSheetDialog(binding.root.context)

        var salesProductAdapter = SalesAdapter(products, userID)
        binding.rcSalesProduct.adapter = salesProductAdapter

        SalesBtnSheetDialog.setContentView(binding.root)
        SalesBtnSheetDialog.show()
    }
}



