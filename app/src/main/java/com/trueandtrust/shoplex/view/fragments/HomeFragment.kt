package com.trueandtrust.shoplex.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.FragmentHomeBinding
import com.trueandtrust.shoplex.model.adapter.CurrentOrdersAdapter
import com.trueandtrust.shoplex.viewmodel.OrdersVM

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var ordersVm: OrdersVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        ordersVm = ViewModelProvider(requireActivity()).get(OrdersVM::class.java)

        if (ordersVm.order.value == null)
            ordersVm.getCurrentOrders()

        requireActivity().title = getString(R.string.home)

        ordersVm.order.observe(viewLifecycleOwner, { orders ->
            binding.rvHome.adapter = CurrentOrdersAdapter(orders)
        })

        return binding.root
    }
}