package com.trueandtrust.shoplex.view.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.FragmentChatBinding
import com.trueandtrust.shoplex.model.adapter.ChatHeadAdapter
import com.trueandtrust.shoplex.model.extra.FirebaseReferences
import com.trueandtrust.shoplex.model.extra.StoreInfo
import com.trueandtrust.shoplex.model.pojo.Chat
import com.trueandtrust.shoplex.model.pojo.ChatHead
import com.trueandtrust.shoplex.model.pojo.Product
import java.util.*


class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding
    private lateinit var chatHeadAdapter: ChatHeadAdapter
    private var chatHeadList = arrayListOf<ChatHead>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(inflater, container, false)

        getChatHeadsInfo()
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = getString(R.string.chat)
        setHasOptionsMenu(true);
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.chat_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId) {
            R.id.search ->
                Toast.makeText(context, R.string.You_CLicked.toString() , Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    fun getChatHeadsInfo() {
        FirebaseReferences.chatRef.whereEqualTo("storeID", StoreInfo.storeID).get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (document.exists()) {
                        var chat: Chat = document.toObject<Chat>()
                        var product = Product()
                        FirebaseReferences.productsRef
                            .document(chat.productIDs[chat.productIDs.size - 1]).get()
                            .addOnSuccessListener { productDocument ->
                                if (productDocument != null) {
                                    product = productDocument.toObject<Product>()!!
                                    //Toast.makeText(context, product.category, Toast.LENGTH_LONG).show()
                                }
                                chatHeadList.add(
                                    ChatHead(
                                        product.productID,
                                        product.storeID,
                                        chat.chatID,
                                        product.name,
                                        "",
                                        product.price,
                                        product.images[0],
                                        chat.userID,
                                        chat.userName,
                                        1
                                    )
                                )
                                if (document.equals(result.last())) {
                                    chatHeadAdapter = ChatHeadAdapter(chatHeadList)
                                    binding.rvChat.adapter = chatHeadAdapter
                                }
                            }
                    }
                }
            }
            .addOnFailureListener { exception ->
                Snackbar.make(binding.root, getString(R.string.Error), Snackbar.LENGTH_LONG).show()
            }
    }
}