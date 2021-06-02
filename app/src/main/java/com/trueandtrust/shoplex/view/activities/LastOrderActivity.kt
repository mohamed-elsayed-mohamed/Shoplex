package com.trueandtrust.shoplex.view.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
//import com.example.graddemo.model.adapter.LastOrderAdapter
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ActivityLastOrderBinding
import com.trueandtrust.shoplex.model.adapter.CurrentOrdersAdapter
import com.trueandtrust.shoplex.viewmodel.OrdersVM

class LastOrderActivity : AppCompatActivity() {
    lateinit var binding : ActivityLastOrderBinding
    lateinit var toolbar: Toolbar
    private lateinit var ordersVm: OrdersVM
    private lateinit var lastOrderAdapter: CurrentOrdersAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLastOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.ordersVm = OrdersVM()
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
//        val order = ArrayList<Orders>()
//        order.add(Orders("Head Phone", 12.0F, "ACCESSORIES", OrderStatus.DELIVERD,"https://i.pinimg.com/236x/35/11/21/351121d0c57db7df186885dc077f7323.jpg"))
//        order.add(Orders("Head Phone", 12.0F, "FASHION", OrderStatus.CURRENT,"https://i.pinimg.com/236x/35/11/21/351121d0c57db7df186885dc077f7323.jpg"))
//        order.add(Orders("Head Phone", 12.0F, "FASHION", OrderStatus.CURRENT,"https://i.pinimg.com/236x/35/11/21/351121d0c57db7df186885dc077f7323.jpg"))


        //lastOrderAdapter = LastOrderAdapter(order)
        //binding.rvLastOrders.adapter = lastOrderAdapter

        ordersVm.getLastOrders()
        ordersVm.lastOrders.observe(this, Observer { orders ->
            lastOrderAdapter = CurrentOrdersAdapter(orders)
            binding.rvLastOrders.adapter = lastOrderAdapter
        })
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }

}