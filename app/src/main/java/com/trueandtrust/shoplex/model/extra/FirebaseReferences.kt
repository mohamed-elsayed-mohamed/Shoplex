package com.trueandtrust.shoplex.model.extra

import android.annotation.SuppressLint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage



object FirebaseReferences {
    // -----------------------> Databases <----------------------- //
    @SuppressLint("StaticFieldLeak")
    private val database = Firebase.firestore
    private val imagesDatabase = FirebaseStorage.getInstance().reference

    // -----------------------> Firestore <----------------------- //
    // Products
    val pendingProductsRef = database.collection("Pending Products")
    val pendingSellersRef = database.collection("Pending Sellers")

    val productsRef = database.collection("Products")
    val locationRef = database.collection("Pending Locations")
    val storeRef = database.collection("Sellers")
val ordersRef= database.collection("Orders")

    // -----------------------> Storage <----------------------- //
    // Products
    val imagesProductsRef = imagesDatabase.child("Products")

    //----------------------> Notifications <---------------------------//
    val notificationTokensRef = database.collection("Notification Center")

}