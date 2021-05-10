package com.trueandtrust.shoplex.view.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.FragmentChatBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class ChatFragment : Fragment() {
    // TODO: Rename and change types of parameters


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val binding : FragmentChatBinding = FragmentChatBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
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





}