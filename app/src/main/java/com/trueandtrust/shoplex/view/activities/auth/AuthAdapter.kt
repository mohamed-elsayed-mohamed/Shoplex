package com.trueandtrust.shoplex.view.activities.auth

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.viewmodel.AuthVM

class AuthAdapter(fm:FragmentManager, val context:Context, private val authVM: AuthVM) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                LoginTabFragment(authVM)
            }
            else -> {
                SignupTabFragment(authVM)
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> {
                this.context.getString(R.string.Login)
            }
            else -> {
                this.context.getString(R.string.Signup)
            }
        }
    }
}