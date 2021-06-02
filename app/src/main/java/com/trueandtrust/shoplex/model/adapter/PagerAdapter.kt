package com.trueandtrust.shoplex.model.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.trueandtrust.shoplex.model.pojo.Product
import com.trueandtrust.shoplex.view.fragments.ProductFragment
import com.trueandtrust.shoplex.view.fragments.ReviewFragment


class PagerAdapter(fm:FragmentManager, val product: Product) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> {
                return ProductFragment(product)
            }
            else -> {
                return ReviewFragment(product.productID)
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "Products"
            }
            else -> {
                return "Reviews"
            }
        }
    }


}