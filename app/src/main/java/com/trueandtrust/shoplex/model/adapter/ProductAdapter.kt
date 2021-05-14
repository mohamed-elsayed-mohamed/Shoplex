package com.trueandtrust.shoplex.model.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.model.pojo.Product


internal class ProductAdapter(
        private val context: Context,
        private val product: ArrayList<Product>
    ) :
    BaseAdapter() {
        private var layoutInflater: LayoutInflater? = null
        private lateinit var imgProduct: ImageView
        private lateinit var tvOldPrice: TextView
        private lateinit var tvNewPrice: TextView
        private lateinit var tvProductName: TextView
        private lateinit var tvReview: TextView
        private lateinit var tvSold: TextView

    override fun getCount(): Int {
            return product.size
        }
        override fun getItem(position: Int): Any? {
            return null
        }
        override fun getItemId(position: Int): Long {
            return 0
        }
        override fun getView(
            position: Int,
            convertView: View?,
            parent: ViewGroup
        ): View? {
            var convertView = convertView
            if (layoutInflater == null) {
                layoutInflater =
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            }
            if (convertView == null) {
                convertView = layoutInflater!!.inflate(R.layout.product_gv, null)

            }
            imgProduct = convertView!!.findViewById(R.id.img_product)
            tvOldPrice = convertView.findViewById(R.id.tv_old_price)
            tvNewPrice = convertView.findViewById(R.id.tv_new_price)
            tvProductName = convertView.findViewById(R.id.tv_product_name)
            tvReview = convertView.findViewById(R.id.tv_review)
            tvSold = convertView.findViewById(R.id.tv_sold)
            val item = product[position]
            Glide.with(context).load(item.productImageUrl).into(imgProduct)

            tvOldPrice.text = item.oldPrice.toString()
            tvNewPrice.text = item.newPrice.toString()
            tvProductName.text = item.name
            tvReview.text = item.rate.toString()
            tvSold.text = item.sold

            return convertView
        }
}