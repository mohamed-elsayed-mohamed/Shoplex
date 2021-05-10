package com.trueandtrust.shoplex.view.activities

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding : ActivityHomeBinding
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var navController : NavController
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout : DrawerLayout
    lateinit var navView : NavigationView

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
       override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.nav_host_fragment)
        bottomNavigationView = binding.navigationView
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(getString(R.string.home))
        bottomNavigationView.setupWithNavController(navController)
        navView =binding.navView
        drawerLayout = binding.drawerLayout

        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        ) {
            override fun onDrawerClosed(view: View) {
                super.onDrawerClosed(view)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
            }
        }
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.setDrawerIndicatorEnabled(true)
        drawerToggle.syncState()
        navView.setNavigationItemSelectedListener{
            when (it.itemId){
                R.id.lastOrderFragment -> {
                    val intent = Intent(this, LastOrderActivity::class.java).apply {
                    }
                    startActivity(intent)
                }
                R.id.locationFragment -> {
                    val intent = Intent(this, StoreLocationActivity::class.java).apply {
                    }
                    startActivity(intent)
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }


        override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
            return NavigationUI.navigateUp(navController,drawerLayout)

        }

}