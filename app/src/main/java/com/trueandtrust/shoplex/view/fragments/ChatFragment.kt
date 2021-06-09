package com.trueandtrust.shoplex.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.FragmentChatBinding
import com.trueandtrust.shoplex.model.adapter.ChatHeadAdapter
import com.trueandtrust.shoplex.viewmodel.ChatHeadVM

class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding
    private lateinit var chatHeadAdapter: ChatHeadAdapter
    private lateinit var chatHeadVm: ChatHeadVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        this.chatHeadVm = ChatHeadVM()
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = getString(R.string.chat)
        // getChatHeadsInfo()
        chatHeadVm.getChatHead()
        chatHeadVm.chatHead.observe(viewLifecycleOwner, Observer { chatHeads ->
            if (chatHeads != null) {
                chatHeadAdapter = ChatHeadAdapter(chatHeads)
                binding.rvChat.adapter = chatHeadAdapter
            }
        })
        return binding.root
    }

    /* private fun getChatHeadsInfo() {
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
                             .addOnFailureListener{
                                 Snackbar.make(binding.root, getString(R.string.Error), Snackbar.LENGTH_LONG).show()
                             }
                     }
                 }
             }
             .addOnFailureListener { exception ->
                 Snackbar.make(binding.root, getString(R.string.Error), Snackbar.LENGTH_LONG).show()
             }
     }*/
}