package com.trueandtrust.shoplex.model.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.trueandtrust.shoplex.R


internal class ProductAdapter(
        private val context: Context,
        private val productInfo: Array<String>,
        private val productImage: IntArray
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
            return productInfo.size
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

            imgProduct.setImageResource(productImage[position])
            tvOldPrice.text = productInfo[position]
            tvNewPrice.text = productInfo[position]
            tvProductName.text = productInfo[position]
            tvReview.text = productInfo[position]
            tvSold.text = productInfo[position]

            return convertView
        }
}