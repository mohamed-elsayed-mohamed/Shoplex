package com.trueandtrust.shoplex.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ActivityHomeBinding
import com.trueandtrust.shoplex.databinding.ActivityLastOrderBinding

class LastOrderActivity : AppCompatActivity() {
    lateinit var binding : ActivityLastOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLastOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)      }
}