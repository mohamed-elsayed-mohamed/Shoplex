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
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.FragmentChatBinding
import com.trueandtrust.shoplex.model.adapter.ChatHeadAdapter
import com.trueandtrust.shoplex.model.extra.FirebaseReferences
import com.trueandtrust.shoplex.model.extra.StoreInfo
import com.trueandtrust.shoplex.model.pojo.Chat
import com.trueandtrust.shoplex.model.pojo.ChatHead
import com.trueandtrust.shoplex.model.pojo.Product


class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding
    private lateinit var chatHeadAdapter: ChatHeadAdapter
    private var chatHeadList = arrayListOf<ChatHead>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity?)!!.supportActionBar!!.title = getString(R.string.chat)
        getChatHeadsInfo()
        return binding.root
    }

    private fun getChatHeadsInfo() {
        FirebaseReferences.chatRef.whereEqualTo("storeID", StoreInfo.storeID).get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (document.exists()) {
                        var chat: Chat = document.toObject()
                        var product = Product()
                        FirebaseReferences.productsRef
                            .document(chat.productIDs.last()).get()
                            .addOnSuccessListener { productDocument ->
                                if (productDocument != null) {
                                    product = productDocument.toObject<Product>()!!
                                    //Toast.makeText(context, product.category, Toast.LENGTH_LONG).show()
                                }
                                chatHeadList.add(
                                    ChatHead(
                                        chat.productIDs,
                                        product.storeID,
                                        chat.chatID,
                                        product.name,
                                        product.price,
                                        product.images[0],
                                        chat.userID,
                                        chat.userName,
                                        chat.unreadStoreMessages
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