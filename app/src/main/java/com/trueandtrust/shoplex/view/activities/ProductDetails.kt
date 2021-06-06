package com.trueandtrust.shoplex.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayout
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.model.adapter.PagerAdapter
import com.trueandtrust.shoplex.databinding.ActivityProductDetailsBinding
import com.trueandtrust.shoplex.model.pojo.Product

class ProductDetails : AppCompatActivity {
    private lateinit var binding: ActivityProductDetailsBinding
    private lateinit var product: Product

    constructor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_product_details)

        product = this.intent.getParcelableExtra(getString(R.string.PRODUCT_KEY))!!

        var pagerAdapter: PagerAdapter = PagerAdapter(supportFragmentManager,this, product)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.viewPager.adapter = pagerAdapter
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager.currentItem = tab.position
                title = tab.text
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        title = binding.viewPager.adapter!!.getPageTitle(0)
    }
}