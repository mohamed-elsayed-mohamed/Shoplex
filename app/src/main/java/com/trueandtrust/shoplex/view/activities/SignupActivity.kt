package com.trueandtrust.shoplex.view.activities

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ActivitySignupBinding
import com.trueandtrust.shoplex.model.extra.FirebaseReferences
import com.trueandtrust.shoplex.model.extra.StoreInfo
import com.trueandtrust.shoplex.model.pojo.Loc
import com.trueandtrust.shoplex.model.pojo.Location.Companion.getAddress
import com.trueandtrust.shoplex.model.pojo.Store
import java.io.IOException
import java.util.*


import java.util.regex.Matcher
import java.util.regex.Pattern

class SignupActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySignupBinding
    private var store : Store = Store()
    private val MAPS_CODE = 202

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignup.setOnClickListener {
            store.image =  "https://img.etimg.com/thumb/width-1200,height-900,imgsize-122620,resizemode-1,msid-75214721/industry/services/retail/future-group-negotiates-rents-for-its-1700-stores.jpg"
            store.name = binding.edName.text.toString()
            store.email = binding.edEmail.text.toString()
            store.phone = binding.edPhone.text.toString()
            store.date = Timestamp.now().toDate()
           // store.location = binding.tvLocation.text
            if (checkEditText()) {
                // Register New Account
                addSeller(store)
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        binding.btnLocation.setOnClickListener {
            startActivityForResult(Intent(this, MapsActivity::class.java), MAPS_CODE)
        }
    }

    //Add Seller
    fun addSeller(store: Store){
        FirebaseReferences.pendingSellersRef.document(store.storeID).set(store).addOnSuccessListener {
            Toast.makeText(baseContext, "Success", Toast.LENGTH_LONG).show()
            StoreInfo.updateStoreInfo(store)
            StoreInfo.updateTokenID()
        }.addOnFailureListener{
            Toast.makeText(baseContext, "Failed: ${it.localizedMessage}", Toast.LENGTH_LONG).show()
            Log.d("FIRE", it.localizedMessage)
        }.addOnCanceledListener {
            Toast.makeText(baseContext, "Canceled", Toast.LENGTH_LONG).show()
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
        if(requestCode == MAPS_CODE){
            if(resultCode == RESULT_OK){
                val location: Parcelable? = data?.getParcelableExtra("Loc")
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