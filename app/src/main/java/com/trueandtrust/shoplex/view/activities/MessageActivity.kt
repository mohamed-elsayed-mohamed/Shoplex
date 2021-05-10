package com.trueandtrust.shoplex.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ActivityMainBinding
import com.trueandtrust.shoplex.databinding.ActivityMessageBinding

class MessageActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMessageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarMessage)
        supportActionBar?.setTitle("")

        binding.imgToolbarChat.setImageResource(R.drawable.product)
        binding.tvToolbarUserChat.text = getString(R.string.user_name)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.message_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.sale -> Toast.makeText(this,"sale",Toast.LENGTH_SHORT).show()

        }
        return false
    }
}