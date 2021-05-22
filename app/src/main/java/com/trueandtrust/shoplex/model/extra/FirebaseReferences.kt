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
    val productsRef = database.collection("Products")
    val locationRef = database.collection("PendingLocations")


    // -----------------------> Storage <----------------------- //
    // Products
    val imagesProductsRef = imagesDatabase.child("Products")

    //-------------------------> Token <------------------------------//

    //eJGMYZKAS1Sqc9cdm_aEh3:APA91bHFwRfKM9dwPrk0h-tC_sqYDjI9RssbOGCNQTQIuDGo68h3An-xX_KHR12a_6IEKuwQnFdx9rGjJV2P2jbk2gnTWLLyTwZmsG68jfRWOpxlkNKilvQOstv_3xOK9qA_y3qUofYi

}