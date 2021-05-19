package com.example.graddemo.model.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.HomeRvBinding
import com.trueandtrust.shoplex.model.pojo.Orders
import com.trueandtrust.shoplex.model.pojo.Product

class HomeAdapter (private val products: ArrayList<Orders>) :
        RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val binding: HomeRvBinding = HomeRvBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
       // viewHolder.imgProduct.setImageResource(dataSet[position])
        viewHolder.tvProductName.text = products[position].name
        viewHolder.tvCategory.text = products[position].category
        viewHolder.tvPrice.text = products[position].price.toString()
        viewHolder.tvLocation.text = products[position].deliveryLoc.toString()

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = products.size

    class ViewHolder(val binding: HomeRvBinding) : RecyclerView.ViewHolder(binding.root) {
        val imgProduct: ImageView
        val tvProductName: TextView
        val tvCategory: TextView
        val tvPrice: TextView
        val tvLocation: TextView

        init {
            // Define click listener for the ViewHolder's View.
            imgProduct = binding.imgProduct
            tvProductName = binding.tvProductName
            tvCategory = binding.tvCategory
            tvPrice = binding.tvPrice
            tvLocation = binding.tvLocation
        }
    }
}