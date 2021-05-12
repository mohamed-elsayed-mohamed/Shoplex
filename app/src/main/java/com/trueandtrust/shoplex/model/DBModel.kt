package com.trueandtrust.shoplex.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.trueandtrust.shoplex.model.enumurations.Category
import com.trueandtrust.shoplex.model.interfaces.INotifyMVP
import com.trueandtrust.shoplex.model.pojo.Product

class DBModel {

    private var notifier: INotifyMVP
    private val database = Firebase.firestore
    private val pendingProductsRef: CollectionReference

    constructor(notifier: INotifyMVP) {
        this.notifier = notifier
        this.pendingProductsRef = database.collection("Pending Products")
    }

    fun addProduct(product: Product, context: Context){
        pendingProductsRef.add(product).addOnSuccessListener {
            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
        }.addOnFailureListener{
            Toast.makeText(context, "Failed: ${it.localizedMessage}", Toast.LENGTH_LONG).show()
            Log.d("FIRE", it.localizedMessage)
        }.addOnCanceledListener {
            Toast.makeText(context, "Canceled", Toast.LENGTH_LONG).show()
        }
    }

    fun getAllProducts(category: Category) : ArrayList<Product> {
        return arrayListOf(Product())
    }

    fun uploadImages(): Boolean{

        return true
    }
}