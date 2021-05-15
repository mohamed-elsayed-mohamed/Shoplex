package com.trueandtrust.shoplex.model.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.trueandtrust.shoplex.databinding.ProductGvBinding
import com.trueandtrust.shoplex.model.pojo.Product


class ProductAdapter(var productsInfo: ArrayList<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ProductGvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            //LayoutInflater.from(parent.context).inflate(R.layout.product_gv, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) =
        holder.bind(productsInfo[position])

    override fun getItemCount() = productsInfo.size

    inner class ProductViewHolder(val binding: ProductGvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            if(product.images.count() > 0) {
                Glide.with(itemView.context).load(product.images[0]).into(binding.imgProduct)
            }
            binding.tvOldPrice.text = product.price.toString()
            binding.tvNewPrice.text = product.newPrice.toString()
            binding.tvProductName.text = product.name
            binding.tvReview.text = product.rate.toString()
            binding.tvSold.text = "15"
            binding.fabAddProduct.setOnClickListener {
                productsInfo.remove(product)
                notifyItemRemoved(adapterPosition)
            }
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "Hello", Toast.LENGTH_SHORT).show()
            }
        }
    }
}