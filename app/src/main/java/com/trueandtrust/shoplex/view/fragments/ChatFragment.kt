package com.trueandtrust.shoplex.view.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.FragmentChatBinding
import com.trueandtrust.shoplex.model.adapter.ChatHeadAdapter
import com.trueandtrust.shoplex.model.adapter.ColorAdapter
import com.trueandtrust.shoplex.model.pojo.ChatHead
import com.trueandtrust.shoplex.model.pojo.Product

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class ChatFragment : Fragment() {
    private lateinit var chatHeadAdapter: ChatHeadAdapter
    // TODO: Rename and change types of parameters
    private var chatHeadList = arrayListOf<ChatHead>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val binding : FragmentChatBinding = FragmentChatBinding.inflate(inflater,container,false)


        chatHeadAdapter = ChatHeadAdapter(getChatHeadsInfo())
        binding.rvChat.adapter = chatHeadAdapter

        (activity as AppCompatActivity?)!!.supportActionBar!!.title =getString(R.string.chat)
        setHasOptionsMenu(true);
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.chat_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId){
            R.id.search ->
                Toast.makeText(context, "You CLicked ", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    fun getChatHeadsInfo() : ArrayList<ChatHead>{
        chatHeadList.add(ChatHead("","","","T-Shirt","",100.0,"https://static.zajo.net/content/mediagallery/zajo_dcat/image/product/types/X/9086.png","Abeer",1))
        chatHeadList.add(ChatHead("","","","T-Shirt","",100.0,"https://static.zajo.net/content/mediagallery/zajo_dcat/image/product/types/X/9086.png","Azhar",1))
        chatHeadList.add(ChatHead("","","","T-Shirt","",100.0,"https://static.zajo.net/content/mediagallery/zajo_dcat/image/product/types/X/9086.png","Heba",1))
        chatHeadList.add(ChatHead("","","","T-Shirt","",100.0,"https://static.zajo.net/content/mediagallery/zajo_dcat/image/product/types/X/9086.png","Abeer",1))
        chatHeadList.add(ChatHead("","","","T-Shirt","",100.0,"https://static.zajo.net/content/mediagallery/zajo_dcat/image/product/types/X/9086.png","Abeer",1))
        chatHeadList.add(ChatHead("","","","T-Shirt","",100.0,"https://static.zajo.net/content/mediagallery/zajo_dcat/image/product/types/X/9086.png","Abeer",1))
        return chatHeadList
    }

}