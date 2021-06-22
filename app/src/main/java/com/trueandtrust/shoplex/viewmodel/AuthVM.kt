package com.trueandtrust.shoplex.viewmodel

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.trueandtrust.shoplex.model.extra.StoreInfo
import com.trueandtrust.shoplex.model.firebase.AuthDBModel
import com.trueandtrust.shoplex.model.interfaces.AuthListener
import com.trueandtrust.shoplex.model.pojo.Store
import com.trueandtrust.shoplex.view.activities.HomeActivity

class AuthVM(val context: Context) : ViewModel(), AuthListener {
    var store: MutableLiveData<Store> = MutableLiveData()
    var email: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()
    var primaryAddress: MutableLiveData<String> = MutableLiveData()

    var isLoginBtnPressed: MutableLiveData<Boolean> = MutableLiveData()
    var isSignupBtnPressed: MutableLiveData<Boolean> = MutableLiveData()

    var isLoginValid: MutableLiveData<Boolean> = MutableLiveData()
    var isSignupValid: MutableLiveData<Boolean> = MutableLiveData()

    private var userDBModel: AuthDBModel

    init {
        this.store.value = Store()
        this.email.value = ""
        this.password.value = ""
        this.primaryAddress.value = ""
        this.userDBModel = AuthDBModel(this, context)
    }

    fun login() {
        userDBModel.login(email.value!!, password.value!!)
    }

    fun createAccount() {
        store.value!!.email = email.value!!
        userDBModel.createAccount(store.value!!, password.value!!)
    }

    override fun onPendingStore() {
        Toast.makeText(context, "Please wait until your account accepted!", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onLoginSuccess(context: Context, store: Store) {
        super.onLoginSuccess(context, store)
        Toast.makeText(context, "Login Success!", Toast.LENGTH_SHORT).show()
        context.startActivity(Intent(context, HomeActivity::class.java))
        (context as AppCompatActivity).finish()
    }

    override fun onLoginFailed() {
        super.onLoginFailed()
        Toast.makeText(context, "Login Failed!", Toast.LENGTH_SHORT).show()
    }

    override fun onAddStoreFailed() {
        super.onAddStoreFailed()
        Toast.makeText(context, "Failed to add store!", Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun logout(context: Context) {
            if (StoreInfo.storeID != null)
                StoreInfo.saveNotification(context, false)
            Firebase.auth.signOut()
            StoreInfo.clear()
            StoreInfo.clearSharedPref(context)
        }
    }
}