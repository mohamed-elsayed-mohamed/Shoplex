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

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //firebase Auth
        auth = Firebase.auth
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

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser

        if (currentUser != null) {
            currentUser.reload()
            reload();
        }
    }

    //Sign In
    private fun signIn(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(
                        baseContext,
                        getString(R.string.login_success),
                        Toast.LENGTH_SHORT
                    ).show()
                    val user = auth.currentUser
                    startActivity(Intent(this, HomeActivity::class.java))
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

        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(baseContext, getString(R.string.email_send), Toast.LENGTH_SHORT)
                    .show()
            }else{
                Toast.makeText(baseContext, getString(R.string.require_email), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun reload() {
        startActivity(Intent(this, HomeActivity::class.java))
    }
}