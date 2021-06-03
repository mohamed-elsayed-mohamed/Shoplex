package com.trueandtrust.shoplex.model.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shoplex.shoplex.model.enumurations.OrderStatus
import com.trueandtrust.shoplex.databinding.HomeRvBinding
import com.trueandtrust.shoplex.model.extra.FirebaseReferences
import com.trueandtrust.shoplex.model.pojo.Order

class CurrentOrdersAdapter(val orders: ArrayList<Order>) :
    RecyclerView.Adapter<CurrentOrdersAdapter.OrdersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        return OrdersViewHolder(
            HomeRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) =
        holder.bind(orders[position])

    override fun getItemCount() = orders.size

    inner class OrdersViewHolder(val binding: HomeRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order) {
            // Your custom view code here
            Glide.with(itemView.context).load(order.product!!.images[0]).into(binding.imgProduct)
            binding.tvProductName.text = order.product?.name
            binding.tvCategory.text = order.product?.category.toString()
            binding.tvPrice.text = order.product?.price.toString()+"EGP"
            binding.tvStatus.text = order.orderStatus.toString()
            if (order.orderStatus.toString()=="Current"){
            binding.btnDeliveryOrder.setOnClickListener {
                FirebaseReferences.ordersRef.document(order.orderID.toString())
                    .update("orderStatus", OrderStatus.Delivered).addOnSuccessListener {
                        Toast.makeText(binding.root.context, "success", Toast.LENGTH_SHORT).show()
                    }
            }
            }else{
                binding.btnDeliveryOrder.visibility = View.GONE
            }

        }
    }
}
