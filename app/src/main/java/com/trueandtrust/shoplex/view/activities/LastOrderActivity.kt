package com.trueandtrust.shoplex.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ActivityHomeBinding
import com.trueandtrust.shoplex.databinding.ActivityLastOrderBinding

class LastOrderActivity : AppCompatActivity() {
    lateinit var binding : ActivityLastOrderBinding
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLastOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(getString(R.string.LastOrder))
        supportActionBar?.setIcon(R.drawable.ic_back)
    }
}