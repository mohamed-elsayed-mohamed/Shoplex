package com.trueandtrust.shoplex.view.activities

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ActivitySignupBinding
import com.trueandtrust.shoplex.model.enumurations.LocationAction
import com.trueandtrust.shoplex.model.extra.FirebaseReferences
import com.trueandtrust.shoplex.model.extra.StoreInfo
import com.trueandtrust.shoplex.model.pojo.Loc
import com.trueandtrust.shoplex.model.pojo.Location.Companion.getAddress
import com.trueandtrust.shoplex.model.pojo.Store
import com.trueandtrust.shoplex.viewmodel.AuthVM
import com.trueandtrust.shoplex.viewmodel.AuthVMFactory
import java.io.IOException
import java.util.*


import java.util.regex.Matcher
import java.util.regex.Pattern

class SignupActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySignupBinding
    private var store : Store = Store()
    private lateinit var authVM: AuthVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        authVM = ViewModelProvider(this, AuthVMFactory(this)).get(AuthVM::class.java)
        binding.storeData = authVM

        binding.btnSignup.setOnClickListener {
            store.image =  "https://img.etimg.com/thumb/width-1200,height-900,imgsize-122620,resizemode-1,msid-75214721/industry/services/retail/future-group-negotiates-rents-for-its-1700-stores.jpg"
            authVM.store.value?.date = Timestamp.now().toDate()
            if (checkEditText()) {
                authVM.createAccount()
                finish()
                //createSellerAccount(binding.edEmail.text.toString(),binding.edPassword.text.toString())
                // Register New Account
                //addSeller(store)

                //startActivity(Intent(this, LoginActivity::class.java))
                //finish()
            }
        }

        binding.btnLocation.setOnClickListener {
            startActivityForResult(Intent(this, MapsActivity::class.java)
                .apply {
                    putExtra(MapsActivity.LOCATION_ACTION, LocationAction.Add.name)
                }, MapsActivity.MAPS_CODE)
        }
    }

    //check EditText
    fun checkEditText(): Boolean {
        when {
            binding.edName.length() == 0 -> binding.edName.error = getString(R.string.Required)
            binding.edName.length() < 5 -> binding.edName.error =
                getString(R.string.min_store_name_err)
            binding.edEmail.length() == 0 -> binding.edEmail.error = getString(R.string.Required)
            !(isEmailValid(binding.edEmail.text.toString())) -> binding.edEmail.error =
                getString(
                    R.string.require_email
                )
            binding.edPassword.length() == 0 -> binding.edPassword.error =
                getString(R.string.Required)
            binding.edPassword.length() < 8 -> binding.edPassword.error =
                getString(R.string.min_password_err)
            binding.edConfirmPassword.length() == 0 -> binding.edConfirmPassword.error =
                getString(R.string.Required)
            binding.edConfirmPassword.text.toString() != binding.edPassword.text.toString() -> binding.edConfirmPassword.error =
                getString(
                    R.string.not_match
                )

            binding.edPhone.length() == 0 -> binding.edPhone.error = getString(R.string.Required)
            store.addresses.size == 0 || store.locations?.size == 0 -> Toast.makeText(this,"Choose Your Location",Toast.LENGTH_LONG).show()
            else -> return true
        }
        return false
    }

    //Email Validation
    fun isEmailValid(email: String?): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }

    //hash password
    /*fun computeMD5Hash(password: String) : String {
        val MD5Hash = StringBuffer()
        try {
            // Create MD5 Hash
            val digest: MessageDigest = MessageDigest.getInstance("MD5")
            digest.update(password.toByteArray())
            val messageDigest: ByteArray = digest.digest()

            for (i in messageDigest.indices) {
                var h = Integer.toHexString(0xFF and messageDigest[i].toInt())
                while (h.length < 2) h = "0$h"
                MD5Hash.append(h)
            }
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return MD5Hash.toString()
    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == MapsActivity.MAPS_CODE){
            if(resultCode == RESULT_OK){
                val location: LatLng? = data?.getParcelableExtra(MapsActivity.LOCATION)
                val address: String? = data?.getStringExtra(MapsActivity.ADDRESS)
                if (location != null) {
                    binding.tvLocation.text = address
                    authVM.store.value!!.addresses.add(address!!)
                    authVM.store.value!!.locations.add(Loc(location.latitude, location.longitude))
                    authVM.primaryAddress.value = address
                }

                if(location != null) {
                    val address = getAddress(location as LatLng,this)
                    store.locations.add(Loc(location.latitude, location.longitude))
                    binding.tvLocation.text = address
                    store.addresses.add(address)
                }
            }
        }
    }
}