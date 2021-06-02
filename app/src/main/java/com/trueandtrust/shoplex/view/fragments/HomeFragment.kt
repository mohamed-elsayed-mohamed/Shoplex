package com.trueandtrust.shoplex.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.FragmentHomeBinding
import com.trueandtrust.shoplex.model.adapter.CurrentOrdersAdapter
import com.trueandtrust.shoplex.model.pojo.Order
import com.trueandtrust.shoplex.viewmodel.OrdersVM

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class HomeFragment : Fragment() {
    private lateinit var homeAdapter: CurrentOrdersAdapter
    lateinit var binding: FragmentHomeBinding
    private lateinit var ordersVm: OrdersVM
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        this.ordersVm = OrdersVM()
        // Inflate the layout for this fragment
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = getString(R.string.home)
        val currentOrders: ArrayList<Order> = arrayListOf()
//
//        currentOrders.add(Orders())
//        currentOrders.add(Orders())
//        currentOrders.add(Orders())
//        currentOrders.add(Orders())
//        currentOrders.add(Orders())
//        currentOrders.add(Orders())

        ordersVm.getCurrentOrders()
        ordersVm.order.observe(viewLifecycleOwner, Observer { orders ->
            homeAdapter = CurrentOrdersAdapter(orders)
            binding.rvHome.adapter = homeAdapter
        })


//        homeAdapter = HomeAdapter(currentOrders)
//        binding.rvHome.adapter = homeAdapter
        return binding.root
    }
}