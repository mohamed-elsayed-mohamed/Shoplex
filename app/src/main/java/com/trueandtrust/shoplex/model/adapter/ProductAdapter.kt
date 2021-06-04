package com.trueandtrust.shoplex.model.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ProductGvBinding
import com.trueandtrust.shoplex.model.firebase.ProductsDBModel
import com.trueandtrust.shoplex.model.interfaces.INotifyMVP
import com.trueandtrust.shoplex.model.pojo.Product
import com.trueandtrust.shoplex.view.activities.AddProductActivity
import com.trueandtrust.shoplex.view.activities.ProductDetails

class ProductAdapter(var productsInfo: ArrayList<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private lateinit var productsDBModel: ProductsDBModel
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ProductGvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) =
        holder.bind(productsInfo[position])

    override fun getItemCount() = productsInfo.size

    inner class ProductViewHolder(val binding: ProductGvBinding) : RecyclerView.ViewHolder(binding.root), INotifyMVP {
        fun bind(product: Product) {
            if(product.images.count() > 0) {
                Glide.with(itemView.context).load(product.images[0]).into(binding.imgProduct)
            }
            binding.tvOldPrice.text = product.price.toString()
            binding.tvNewPrice.text = product.newPrice.toString()
            binding.tvProductName.text = product.name
            binding.tvReview.text = product.rate.toString()
            binding.tvSold.text = "15"
            binding.fabDeleteProduct.setOnClickListener {
                productsDBModel = ProductsDBModel(this)
                productsDBModel.deleteProduct(product.productID, product.images)
                //productsInfo.remove(product)
                //notifyItemRemoved(adapterPosition)
            }
            itemView.setOnClickListener {
                val context: Context = binding.root.context

//                context.startActivity(Intent(context, AddProductActivity::class.java).apply {
//                    this.putExtra(context.getString(R.string.PRODUCT_KEY), product)
//                })


                context.startActivity(Intent(context, ProductDetails::class.java).apply {
                    this.putExtra(context.getString(R.string.PRODUCT_KEY), product)
                })

            }
        }
        override fun onProductRemoved() {
            //Toast.makeText(this.binding.root.context,R.string. Product.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}