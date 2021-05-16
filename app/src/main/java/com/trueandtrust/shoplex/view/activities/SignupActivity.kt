package com.trueandtrust.shoplex.view.activities

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ActivitySignupBinding
import com.trueandtrust.shoplex.model.pojo.Store
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.regex.Matcher
import java.util.regex.Pattern

class SignupActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySignupBinding
    private var store : Store = Store()
    private val database = Firebase.firestore
    private lateinit var pendingSellerRef: CollectionReference
    private val MAPS_CODE = 202

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignup.setOnClickListener {
            store.name = binding.edName.text.toString()
            store.email = binding.edEmail.text.toString()
            store.phone = binding.edPhone.text.toString()
            store.password = computeMD5Hash(binding.edPassword.text.toString())
            checkEditText(store)
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

        binding.btnLocation.setOnClickListener {
            startActivityForResult(Intent(this, MapsActivity::class.java), MAPS_CODE)
        }
    }

    //Add Seller
    fun addSeller(store: Store){
        pendingSellerRef = database.collection("Pending Sellers")
        pendingSellerRef.add(store).addOnSuccessListener {
            Toast.makeText(baseContext, "Success", Toast.LENGTH_LONG).show()
        }.addOnFailureListener{
            Toast.makeText(baseContext, "Failed: ${it.localizedMessage}", Toast.LENGTH_LONG).show()
            Log.d("FIRE", it.localizedMessage)
        }.addOnCanceledListener {
            Toast.makeText(baseContext, "Canceled", Toast.LENGTH_LONG).show()
        }
    }

    //check EditText
    fun checkEditText(store: Store){
        when{
            binding.edName.length() == 0 -> binding.edName.error = getString(R.string.Required)
            binding.edName.length() < 5 -> binding.edName.error = getString(R.string.min_store_name_err)
            binding.edEmail.length() == 0 -> binding.edEmail.error = getString(R.string.Required)
            isEmailValid(binding.edEmail.text.toString()) != true -> binding.edEmail.error = getString(
                R.string.require_email
            )

            binding.edPassword.length() == 0 -> binding.edPassword.error = getString(R.string.Required)
            binding.edPassword.length() < 8 -> binding.edPassword.error = getString(R.string.min_password_err)
            binding.edConfirmPassword.length() == 0 -> binding.edConfirmPassword.error = getString(R.string.Required)
            binding.edConfirmPassword.text.toString().equals(binding.edPassword.text.toString()) != true -> binding.edConfirmPassword.error = getString(
                R.string.not_match
            )

            binding.edPhone.length() == 0 -> binding.edPhone.error = getString(R.string.Required)
            else -> addSeller(store)

        }
    }

    //Email Validation
    fun isEmailValid(email: String?): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }

    //hash password
    fun computeMD5Hash(password: String) : String {
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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == MAPS_CODE){
            if(resultCode == RESULT_OK){
                val location: Parcelable? = data?.getParcelableExtra("Loc")
                if(location != null) {
                    binding.tvLocation.text = location.toString()
                }
            }
        }
    }
}