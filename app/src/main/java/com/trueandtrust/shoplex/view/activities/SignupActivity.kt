package com.trueandtrust.shoplex.view.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Timestamp
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ActivitySignupBinding
import com.trueandtrust.shoplex.model.enumurations.LocationAction
import com.trueandtrust.shoplex.model.pojo.Location
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
    private val OPEN_GALLERY_CODE = 200
    var uri:Uri? =null

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
            }
        }

        binding.btnLocation.setOnClickListener {
            startActivityForResult(Intent(this, MapsActivity::class.java)
                .apply {
                    putExtra(MapsActivity.LOCATION_ACTION, LocationAction.Add.name)
                }, MapsActivity.MAPS_CODE)
        }
        binding.imgLogo.setOnClickListener{
            openGallary()
        }
        onEditTextChanged()
    }
    private fun openGallary() {
        var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
        intent.type = "image/*"
        startActivityForResult(intent, OPEN_GALLERY_CODE)
    }

    private fun onEditTextChanged(){
        binding.edName.addTextChangedListener {
            binding.tiName.error = null
        }
        binding.edEmail.addTextChangedListener {
            binding.tiEmail.error = null
        }
        binding.edPassword.addTextChangedListener {
            binding.tiPassword.error = null
        }
        binding.edConfirmPassword.addTextChangedListener {
            binding.tiConfirmPassword.error = null
        }
        binding.edPhone.addTextChangedListener {
            binding.tiPhone.error = null
        }
        binding.tvLocation.addTextChangedListener{
            binding.tvLocation.error = null
        }
    }

    //check EditText
    private fun checkEditText(): Boolean {
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

            binding.edPhone.text.toString().isEmpty() -> binding.tiPhone.error =
                getString(R.string.Required)

            !isValidMobile(binding.edPhone.text.toString()) -> binding.tiPhone.error =
                "Please Enter Valid Mobile"


//            binding.edPhone.length() == 0 -> binding.edPhone.error = getString(R.string.Required)
            store.addresses.size == 0 || store.locations?.size == 0 -> Toast.makeText(this,"Choose Your Location",Toast.LENGTH_LONG).show()

            authVM.store.value?.image.isNullOrEmpty() -> Toast.makeText(
                this,
                "Please, Choose Image",
                Toast.LENGTH_SHORT
            ).show()
            else -> return true
        }
        return false
    }

    //Email Validation
    private fun isEmailValid(email: String?): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }

    private fun isValidMobile(phone: String): Boolean {
        return if (!Pattern.matches("[a-zA-Z]+", phone)) {
            phone.length > 11 && phone.length <= 13
        } else false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == MapsActivity.MAPS_CODE){
            if(resultCode == RESULT_OK){
                val location: LatLng? = data?.getParcelableExtra(MapsActivity.LOCATION)
                val address: String? = data?.getStringExtra(MapsActivity.ADDRESS)
                if (location != null) {
                    binding.tvLocation.text = address
                    authVM.store.value!!.addresses.add(address!!)
                    authVM.store.value!!.locations.add(Location(location.latitude, location.longitude))
                    authVM.primaryAddress.value = address
                }

                if(location != null && address != null) {
                    store.locations.add(Location(location.latitude, location.longitude))
                    binding.tvLocation.text = address
                    store.addresses.add(address)
                }
            }
        }else if(requestCode == OPEN_GALLERY_CODE){
         if(resultCode== RESULT_OK){
             if (data==null || data.data == null){
                 return

             }
             uri=data.data
             authVM.store.value!!.image=uri.toString()
             try {
                 val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                 binding.imgLogo.setImageBitmap(bitmap)
             } catch (e: IOException) {
                 e.printStackTrace()
             }
         }
        }
    }
}