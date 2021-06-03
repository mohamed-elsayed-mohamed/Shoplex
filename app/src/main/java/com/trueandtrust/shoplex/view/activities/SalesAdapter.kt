package com.trueandtrust.shoplex.view.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.trueandtrust.shoplex.R
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.trueandtrust.shoplex.databinding.SaleProductItemRowBinding
import com.trueandtrust.shoplex.model.pojo.Product

class SalesAdapter(val SalesProduct: ArrayList<Product>) :
    RecyclerView.Adapter<SalesAdapter.SalesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesViewHolder {
        return SalesViewHolder(
            SaleProductItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SalesViewHolder, position: Int) =
        holder.bind(SalesProduct[position])

    override fun getItemCount() = SalesProduct.size

    inner class SalesViewHolder(val binding: SaleProductItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product) {
            Glide.with(itemView.context).load(item.images[0]).into(binding.imgSaleProduct)
            binding.tvSalesProductName.text = item.name
            binding.tvProductCategory.text = item.category
            binding.tvSalesPrice.text = item.price.toString()
            val arrayDiscountTypeAdapter = ArrayAdapter(binding.root.context, R.layout.dropdown_discount_type_item,binding.root.context.resources.getStringArray(R.array.arr_type_discount))
            binding.actDiscountType.setAdapter(arrayDiscountTypeAdapter)
            binding.btnConfirmSales.setOnClickListener {

                Snackbar.make(binding.root, binding.root.context.getString(R.string.confirm), Snackbar.LENGTH_LONG).show()
            }

        }
    }
}