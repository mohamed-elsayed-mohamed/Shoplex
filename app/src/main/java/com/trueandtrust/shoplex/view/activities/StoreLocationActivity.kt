package com.trueandtrust.shoplex.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ActivityLastOrderBinding
import com.trueandtrust.shoplex.databinding.ActivityStoreLocationBinding

class StoreLocationActivity : AppCompatActivity() {
    lateinit var binding : ActivityStoreLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}