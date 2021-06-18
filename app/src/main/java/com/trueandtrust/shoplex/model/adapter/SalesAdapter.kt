package com.trueandtrust.shoplex.model.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.trueandtrust.shoplex.R
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.trueandtrust.shoplex.model.enumurations.DiscountType
import com.trueandtrust.shoplex.databinding.SaleProductItemRowBinding
import com.trueandtrust.shoplex.model.extra.FirebaseReferences
import com.trueandtrust.shoplex.model.pojo.Product
import com.trueandtrust.shoplex.model.pojo.SpecialDiscount

class SalesAdapter(private val salesProduct: ArrayList<Product>, val userID: String) :
    RecyclerView.Adapter<SalesAdapter.SalesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesViewHolder {
        return SalesViewHolder(
            SaleProductItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SalesViewHolder, position: Int) =
        holder.bind(salesProduct[position])

    override fun getItemCount() = salesProduct.size

    inner class SalesViewHolder(val binding: SaleProductItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            Glide.with(itemView.context).load(product.images.firstOrNull()).error(R.drawable.product).into(binding.imgSaleProduct)
            binding.tvSalesProductName.text = product.name
            binding.tvProductCategory.text = product.category
            binding.tvSalesPrice.text = product.price.toString()
            binding.tvProductPrice.text = product.price.toString()
            val arrayDiscountTypeAdapter = ArrayAdapter(binding.root.context, R.layout.dropdown_discount_type_item,
                DiscountType.values())
            binding.actDiscountType.setText(DiscountType.Percentage.name)
            binding.actDiscountType.setAdapter(arrayDiscountTypeAdapter)
            binding.actDiscountType.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                if(position==0){
                    binding.tiSaleDiscount.helperText = "0-90%"
                }else {
                    binding.tiSaleDiscount.helperText = "0-100"

                }
            }

            binding.btnConfirmSales.setOnClickListener {
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