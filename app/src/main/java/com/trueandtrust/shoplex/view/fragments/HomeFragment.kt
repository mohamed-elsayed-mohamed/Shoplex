package com.trueandtrust.shoplex.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.graddemo.model.adapter.HomeAdapter
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.FragmentChatBinding
import com.trueandtrust.shoplex.databinding.FragmentHomeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class HomeFragment : Fragment() {
    private lateinit var homeAdapter: HomeAdapter
    lateinit var binding : FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding  = FragmentHomeBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = getString(R.string.home)
        val data = arrayOf("fashion", "yyyyy", "gggggg")

        homeAdapter = HomeAdapter(data)
        binding.rvHome.adapter = homeAdapter
        return binding.root
    }
}