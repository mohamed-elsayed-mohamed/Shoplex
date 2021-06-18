package com.trueandtrust.shoplex.model.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.trueandtrust.shoplex.model.enumurations.OrderStatus
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.HomeRvBinding
import com.trueandtrust.shoplex.model.firebase.OrdersDBModel
import com.trueandtrust.shoplex.model.maps.LocationManager
import com.trueandtrust.shoplex.model.pojo.Order
import com.trueandtrust.shoplex.view.activities.DetailsActivity

class OrdersAdapter(val orders: ArrayList<Order>) :
    RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        context = parent.context

        return OrdersViewHolder(
            HomeRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) = holder.bind(orders[position])

    override fun getItemCount() = orders.size

    inner class OrdersViewHolder(val binding: HomeRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order) {

            Glide.with(itemView.context).load(order.product!!.images.firstOrNull()).error(R.drawable.product).into(binding.imgProduct)
            binding.order = order

            if (order.orderStatus == OrderStatus.Current) {
                binding.btnDeliveryOrder.setOnClickListener {
                    OrdersDBModel.deliverOrder(order.orderID.toString())
                    orders.removeAt(bindingAdapterPosition)
                    notifyItemRemoved(bindingAdapterPosition)
                }
                binding.btnShowRoute.visibility = View.VISIBLE
            } else {
                binding.btnDeliveryOrder.visibility = View.INVISIBLE
            }

            binding.btnShowRoute.setOnClickListener {
                if(order.deliveryLoc != null) {
                    LocationManager.getInstance(context).launchGoogleMaps(order.deliveryLoc!!)
                }else{
                    Toast.makeText(context, "Can't Identify Customer Location", Toast.LENGTH_SHORT).show()
                }
            }

            itemView.setOnClickListener {
                context.startActivity(Intent(context, DetailsActivity::class.java).apply {
                    this.putExtra(context.getString(R.string.PRODUCT_KEY), order.product)
                })
            }
        }
    }
}
