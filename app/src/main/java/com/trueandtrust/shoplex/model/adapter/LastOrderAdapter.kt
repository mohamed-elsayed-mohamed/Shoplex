package com.trueandtrust.shoplex.model.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.model.pojo.Orders

class LastOrderAdapter (private val product: ArrayList<Orders>) :
        RecyclerView.Adapter<LastOrderAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.home_rv, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = product[position]
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Glide.with(viewHolder.itemView.context).load(item.productImageUrl).into(viewHolder.imgProduct)
        viewHolder.tvproductName.text = item.name
        viewHolder.tvCategory.text = item.category.toString()
        viewHolder.tvPrice.text = item.price.toString()
//        viewHolder.tvStatus.text = item.orderStatus.toString()

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = product.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgProduct: ImageView
        val tvproductName:TextView
        val tvCategory: TextView
        val tvPrice: TextView
//        val tvStatus: TextView


        init {
            // Define click listener for the ViewHolder's View.
            imgProduct = view.findViewById(R.id.img_product)
            tvproductName = view.findViewById(R.id.tv_product_name)
            tvCategory=view.findViewById(R.id.tvCategory)
            tvPrice = view.findViewById(R.id.tv_price)
//            tvStatus = view.findViewById(R.id.tv_status)

        }
    }
}