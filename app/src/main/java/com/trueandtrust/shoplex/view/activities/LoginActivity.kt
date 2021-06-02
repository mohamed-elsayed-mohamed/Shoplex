package com.trueandtrust.shoplex.view.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.system.Os.close
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ActivityLoginBinding
import com.trueandtrust.shoplex.model.extra.StoreInfo
import com.trueandtrust.shoplex.model.firebase.StoreDBModel
import com.trueandtrust.shoplex.model.interfaces.INotifyMVP
import com.trueandtrust.shoplex.model.pojo.Store

class LoginActivity : AppCompatActivity(), INotifyMVP {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Not Have Account
        binding.tvCreateAccount.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        //Forget Password
        binding.tvForgetPass.setOnClickListener {
            openDialog()
        }

        //Login button
        binding.btnLogin.setOnClickListener {
            signIn(binding.edEmail.text.toString(), binding.edPassword.text.toString())
        }
    }

    //Sign In
    private fun signIn(email: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    StoreDBModel(this).getStoreByMail(email)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, getString(R.string.authentication_fail),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    //openDialog
    private fun openDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.forget_password))
        val view: View = layoutInflater.inflate(R.layout.dialog_forget_password, null)
        builder.setView(view)
        val email: EditText = view.findViewById(R.id.edEmailDialog)
        builder.setPositiveButton(
            getString(R.string.reset),
            DialogInterface.OnClickListener { _, _ ->
                when {
                    email.text.toString().isEmpty() -> email.error = getString(R.string.Required)
                    else -> forgetPassword(email.text.toString())
                }
            })

        builder.setNegativeButton(
            getString(R.string.close_dialog),
            DialogInterface.OnClickListener { _, _ -> })
        builder.show()
    }

    //Forget Password
    private fun forgetPassword(email: String) {
        Firebase.auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(baseContext, getString(R.string.email_send), Toast.LENGTH_SHORT)
                    .show()
            }else{
                Toast.makeText(baseContext, getString(R.string.require_email), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onStoreInfoReady(isAccountActive: Boolean) {
        if(isAccountActive) {
            StoreInfo.updateTokenID()
            // Sign in success, update UI with the signed-in user's information
            Toast.makeText(baseContext, getString(R.string.login_success), Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext, HomeActivity::class.java))
            finish()
        }else{
            Toast.makeText(applicationContext, getString(R.string.Please_wait_until_your_account_accepted), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStoreInfoFailed() {
        super.onStoreInfoFailed()
    }
}