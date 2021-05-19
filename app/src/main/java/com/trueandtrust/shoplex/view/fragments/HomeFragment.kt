package com.trueandtrust.shoplex.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.graddemo.model.adapter.HomeAdapter
import com.google.android.gms.maps.model.LatLng
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.FragmentChatBinding
import com.trueandtrust.shoplex.databinding.FragmentHomeBinding
import com.trueandtrust.shoplex.model.enumurations.Category
import com.trueandtrust.shoplex.model.pojo.ChatHead
import com.trueandtrust.shoplex.model.pojo.Product

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
        val product = ArrayList<Product>()
        product.add(Product("Head Phone", 12.0F, "ACCESSORIES", LatLng(0.0, 0.0),"https://cdn.cliqueinc.com/posts/285870/best-cheap-spring-accessories-285870-1583111706473-main.750x0c.jpg?interlace=true&quality=70"))
        product.add(Product("Head Phone", 12.0F,"FASHION", LatLng(0.0, 0.0),"https://cdn.cliqueinc.com/posts/285870/best-cheap-spring-accessories-285870-1583111706473-main.750x0c.jpg?interlace=true&quality=70"))
        product.add(Product("Head Phone", 12.0F, "FASHION", LatLng(0.0, 0.0),"https://i.pinimg.com/236x/35/11/21/351121d0c57db7df186885dc077f7323.jpg"))
        product.add(Product("Head Phone", 12.0F, "FASHION", LatLng(0.0, 0.0),"https://i.pinimg.com/236x/35/11/21/351121d0c57db7df186885dc077f7323.jpg"))
        product.add(Product("Head Phone", 12.0F, "FASHION", LatLng(0.0, 0.0),"https://i.pinimg.com/236x/35/11/21/351121d0c57db7df186885dc077f7323.jpg"))
        product.add(Product("Head Phone", 12.0F, "FASHION", LatLng(0.0, 0.0),"https://i.pinimg.com/236x/35/11/21/351121d0c57db7df186885dc077f7323.jpg"))
        product.add(Product("Head Phone", 12.0F, "FASHION", LatLng(0.0, 0.0),"https://i.pinimg.com/236x/35/11/21/351121d0c57db7df186885dc077f7323.jpg"))
        product.add(Product("Head Phone", 12.0F, "FASHION", LatLng(0.0, 0.0),"https://i.pinimg.com/236x/35/11/21/351121d0c57db7df186885dc077f7323.jpg"))
        product.add(Product("Head Phone", 12.0F, "FASHION", LatLng(0.0, 0.0),"https://i.pinimg.com/236x/35/11/21/351121d0c57db7df186885dc077f7323.jpg"))


        homeAdapter = HomeAdapter(product)
        binding.rvHome.adapter = homeAdapter
        return binding.root
    }
}