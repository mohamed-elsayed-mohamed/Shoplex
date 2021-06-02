package com.trueandtrust.shoplex.model.firebase


import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.trueandtrust.shoplex.model.extra.FirebaseReferences
import com.trueandtrust.shoplex.model.interfaces.AuthListener
import com.trueandtrust.shoplex.model.pojo.Store

class AuthDBModel(val listener: AuthListener, val context: Context) {
    fun login(email: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    loginWithEmail(email)
                } else {
                    listener.onLoginFailed()
                }
            }
    }

    fun createAccount(store: Store, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(store.email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    addNewStore(store)
                } else {
                    Toast.makeText(context, "Auth Failed!", Toast.LENGTH_SHORT).show()
                    listener.onAddStoreFailed()
                }
            }
    }

    private fun addNewStore(store: Store) {
        val ref: DocumentReference = FirebaseReferences.pendingSellersRef.document()
        store.storeID = ref.id
        ref.set(store).addOnSuccessListener {
            //listener.onAddNewStore(store)
            Toast.makeText(context, "Success to create your account!", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            //listener.onAddNewStore(null)
            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show()
            listener.onAddStoreFailed()
        }
    }

    private fun loginWithEmail(userEmail: String) {
        FirebaseReferences.storeRef.whereEqualTo("email", userEmail).get()
            .addOnSuccessListener {
                var store: Store?
                when {
                    it.documents.count() > 0 -> {
                        store = it.documents[0].toObject()!!
                        listener.onLoginSuccess(context, store)
                    }
                    else -> {
                        // listener.onLoginFailed()
                        checkPendingStore(userEmail)
                    }
                }
            }.addOnFailureListener {
                listener.onLoginFailed()
            }
    }

    private fun checkPendingStore(userEmail: String){
        FirebaseReferences.pendingSellersRef.whereEqualTo("email", userEmail).get()
            .addOnSuccessListener {
                // var store: Store?
                when {
                    it.documents.count() > 0 -> {
                        // store = it.documents[0].toObject()!!
                        listener.onPendingStore()
                    }
                    else -> {
                        listener.onLoginFailed()
                    }
                }
            }.addOnFailureListener {
                listener.onLoginFailed()
            }
    }
}