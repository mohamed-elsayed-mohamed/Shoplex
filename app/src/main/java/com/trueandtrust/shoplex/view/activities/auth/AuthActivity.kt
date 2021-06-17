package com.trueandtrust.shoplex.view.activities.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.trueandtrust.shoplex.databinding.ActivityAuthBinding
import com.trueandtrust.shoplex.viewmodel.AuthVM
import com.trueandtrust.shoplex.viewmodel.AuthVMFactory
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private lateinit var authVM: AuthVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        authVM = ViewModelProvider(this, AuthVMFactory(this)).get(AuthVM::class.java)
        binding.tabLatout.setupWithViewPager(binding.viewPager)
        binding.viewPager.adapter = AuthAdapter(supportFragmentManager, this, authVM)

        binding.tabLatout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager.currentItem = tab.position
                title = tab.text
                btnLogin.text = title
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        binding.btnLogin.setOnClickListener {
            authVM.isLoginValid.value = false
            authVM.isSignupValid.value = false
            if (binding.viewPager.currentItem == 0) {
                authVM.isLoginBtnPressed.value = true
                authVM.isLoginValid.observe(this, {
                    if(it){
                        authVM.login()
                        authVM.isLoginValid.value = false
                    }
                })
            } else {
                authVM.isSignupBtnPressed.value = true
                authVM.isSignupValid.observe(this, {
                    if(it){
                        authVM.createAccount()
                        finish()
                        authVM.isSignupValid.value = false
                    }
                })
            }
        }
    }
}