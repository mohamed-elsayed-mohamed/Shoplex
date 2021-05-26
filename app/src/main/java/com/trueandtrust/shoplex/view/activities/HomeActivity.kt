package com.trueandtrust.shoplex.view.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ActivityHomeBinding
import com.trueandtrust.shoplex.databinding.NavHeaderBinding
import com.trueandtrust.shoplex.model.extra.StoreInfo


class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var navController: NavController
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

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
        navView = binding.navView
        drawerLayout = binding.drawerLayout

        //subscribe Topic
        Firebase.messaging.subscribeToTopic("Notify")
        //get Token
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("Token", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = getString(R.string.msg_token_fmt, token)
            Log.d("Token", msg)
            //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })

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
       val switchId = navView.menu.getItem(3).actionView.findViewById<SwitchCompat>(R.id.switch_id)
           switchId.setOnClickListener {
            if(switchId.isChecked()){
                Toast.makeText(applicationContext,"checked",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(applicationContext,"not checked",Toast.LENGTH_SHORT).show()

            }
        }

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.lastOrderFragment -> {
                    startActivity(Intent(this, LastOrderActivity::class.java))
                }
                R.id.locationFragment -> {
                    startActivity(Intent(this, StoreLocationActivity::class.java))
                }

                R.id.Logout -> {
                    Firebase.auth.signOut()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }


        val header: NavHeaderBinding = NavHeaderBinding.inflate(layoutInflater, this.navView, true)
        if (StoreInfo.image != null)
            Glide.with(this).load(StoreInfo.image).into(header.navHeaderImage)
        header.tvStoreName.text = StoreInfo.name
        header.tvStoreEmail.text = StoreInfo.email
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return NavigationUI.navigateUp(navController, drawerLayout)

    }


    override fun onBackPressed() {
        val seletedItemId = bottomNavigationView.selectedItemId

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            if (seletedItemId == R.id.homeFragment) {
                finishAffinity()
            } else {
                //supportActionBar?.setTitle("Home")
                findNavController(R.id.nav_host_fragment).popBackStack()
            }

        }
    }


}