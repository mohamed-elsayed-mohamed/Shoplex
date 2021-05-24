package com.trueandtrust.shoplex.model.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.HomeRvBinding
import com.trueandtrust.shoplex.databinding.ProductGvBinding
import com.trueandtrust.shoplex.model.firebase.ProductsDBModel
import com.trueandtrust.shoplex.model.interfaces.INotifyMVP
import com.trueandtrust.shoplex.model.pojo.Order
import com.trueandtrust.shoplex.model.pojo.Orders
import com.trueandtrust.shoplex.model.pojo.Product
import com.trueandtrust.shoplex.view.activities.AddProductActivity

class LastOrdersAdapter(val orders: ArrayList<Order>) : RecyclerView.Adapter<LastOrdersAdapter.ProductViewHolder>() {
    private lateinit var productsDBModel: ProductsDBModel
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            HomeRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) =
        holder.bind(orders [position])

    override fun getItemCount() = orders.size

    inner class ProductViewHolder(val binding: HomeRvBinding) :
        RecyclerView.ViewHolder(binding.root),
        INotifyMVP {
        fun bind(order: Order) {
            Glide.with(itemView.context).load(order.product!!.images[0]).into(binding.imgProduct)
            binding.tvProductName.text = order.product?.name
            binding.tvCategory.text = order.product?.category.toString()
            binding.tvPrice.text = order.product?.price.toString()
            binding.tvStatus.text = order.orderStatus.toString()
        }

    }
}