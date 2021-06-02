package com.trueandtrust.shoplex.view.activities

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ActivityLoginBinding
import com.trueandtrust.shoplex.viewmodel.AuthVM
import com.trueandtrust.shoplex.viewmodel.AuthVMFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var authVM: AuthVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        authVM = ViewModelProvider(this, AuthVMFactory(this)).get(AuthVM::class.java)
        binding.storeData = authVM

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
            authVM.login()
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
}