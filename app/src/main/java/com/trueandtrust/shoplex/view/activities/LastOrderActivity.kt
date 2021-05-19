package com.trueandtrust.shoplex.view.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.maps.model.LatLng
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ActivityLastOrderBinding
import com.trueandtrust.shoplex.model.adapter.LastOrderAdapter
import com.trueandtrust.shoplex.model.enumurations.Category
import com.trueandtrust.shoplex.model.enumurations.OrderStatus
import com.trueandtrust.shoplex.model.pojo.Orders
import com.trueandtrust.shoplex.model.pojo.Product

class LastOrderActivity : AppCompatActivity() {
    lateinit var binding : ActivityLastOrderBinding
    lateinit var toolbar: Toolbar
    private lateinit var lastOrderAdapter: LastOrderAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLastOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = getString(R.string.LastOrder)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        }
        if (getSupportActionBar() != null){
            getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar()?.setDisplayShowHomeEnabled(true);
        }
        val order = ArrayList<Orders>()
        order.add(Orders("",OrderStatus.CURRENT,"Diamond",10.0F,"Fashion","https://i.pinimg.com/236x/35/11/21/351121d0c57db7df186885dc077f7323.jpg"))
        order.add(Orders("",OrderStatus.CURRENT,"Diamond",10.0F,"Fashion","https://i.pinimg.com/236x/35/11/21/351121d0c57db7df186885dc077f7323.jpg"))
        order.add(Orders("",OrderStatus.CURRENT,"Diamond",10.0F,"Fashion","https://i.pinimg.com/236x/35/11/21/351121d0c57db7df186885dc077f7323.jpg"))


        lastOrderAdapter = LastOrderAdapter(order)
        binding.rvLastOrders.adapter = lastOrderAdapter

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }

}