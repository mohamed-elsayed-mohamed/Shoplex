package com.trueandtrust.shoplex.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.trueandtrust.shoplex.R
import androidx.recyclerview.widget.RecyclerView
import com.trueandtrust.shoplex.databinding.PropertyItemRowBinding
import com.trueandtrust.shoplex.model.pojo.Orders

class OrdersAdapter(val ordersList: ArrayList<Orders>, val listStr: ArrayList<String>) :
    RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(
            PropertyItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) =
        holder.bind(ordersList[position])

    override fun getItemCount() = ordersList.size

    inner class OrderViewHolder(val binding: PropertyItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Orders) {
            binding.root.context
        }
    }
}