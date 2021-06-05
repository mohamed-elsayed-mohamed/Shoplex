package com.trueandtrust.shoplex.model.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.trueandtrust.shoplex.R
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shoplex.shoplex.model.enumurations.DiscountType
import com.trueandtrust.shoplex.databinding.SaleProductItemRowBinding
import com.trueandtrust.shoplex.model.extra.FirebaseReferences
import com.trueandtrust.shoplex.model.pojo.Product
import com.trueandtrust.shoplex.model.pojo.SpecialDiscount

class SalesAdapter(val SalesProduct: ArrayList<Product>, val userID: String) :
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
        fun bind(product: Product) {
            Glide.with(itemView.context).load(product.images[0]).into(binding.imgSaleProduct)
            binding.tvSalesProductName.text = product.name
            binding.tvProductCategory.text = product.category
            binding.tvSalesPrice.text = product.price.toString()
            val arrayDiscountTypeAdapter = ArrayAdapter(binding.root.context, R.layout.dropdown_discount_type_item,binding.root.context.resources.getStringArray(R.array.arr_type_discount))
            binding.actDiscountType.setAdapter(arrayDiscountTypeAdapter)
            binding.btnConfirmSales.setOnClickListener {
                // Toast.makeText(binding.root.context, "User: " + userID + ", ID: " + product.productID, Toast.LENGTH_SHORT).show()
                addDiscount(product)
            }

        }

        private fun addDiscount(product: Product){
            val discount = binding.edSaleDiscount.text.toString().toFloat()
            val discountType = DiscountType.valueOf(binding.actDiscountType.text.toString())
            val specialDiscount = SpecialDiscount(discount, discountType)
            FirebaseReferences.productsRef.document(product.productID)
                .collection("Special Discounts")
                .document(userID)
                .set(specialDiscount).addOnCompleteListener {
                Toast.makeText(binding.root.context, "Discount Added Successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(binding.root.context, "Failed to add Discount", Toast.LENGTH_SHORT).show()
            }
        }
    }
}