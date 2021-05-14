package com.trueandtrust.shoplex.view.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.FragmentChatBinding
import com.trueandtrust.shoplex.model.adapter.ChatHeadAdapter
import com.trueandtrust.shoplex.model.pojo.ChatHead


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class ChatFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var chatHeadAdapter: ChatHeadAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val binding : FragmentChatBinding = FragmentChatBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        (activity as AppCompatActivity?)!!.supportActionBar!!.title =getString(R.string.chat)
        setHasOptionsMenu(true);

        val chatHead = ArrayList<ChatHead>()
        chatHead.add(ChatHead("Head Phone",12.0, "https://i.pinimg.com/236x/35/11/21/351121d0c57db7df186885dc077f7323.jpg","Azhar",5))
        chatHead.add(ChatHead("Head Phone",12.0, "https://i.pinimg.com/236x/35/11/21/351121d0c57db7df186885dc077f7323.jpg","Azhar",5))
        chatHead.add(ChatHead("Head Phone",12.0, "https://i.pinimg.com/236x/35/11/21/351121d0c57db7df186885dc077f7323.jpg","Azhar",5))
        chatHead.add(ChatHead("Head Phone",12.0, "https://i.pinimg.com/236x/35/11/21/351121d0c57db7df186885dc077f7323.jpg","Azhar",5))
        chatHead.add(ChatHead("Head Phone",12.0, "https://i.pinimg.com/236x/35/11/21/351121d0c57db7df186885dc077f7323.jpg","Azhar",5))


        chatHeadAdapter = ChatHeadAdapter(chatHead)
        binding.rvChat.addItemDecoration(
            DividerItemDecoration(
                binding.rvChat.getContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        binding.rvChat.adapter = chatHeadAdapter


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





}