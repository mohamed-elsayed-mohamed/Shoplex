package com.trueandtrust.shoplex.model

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.trueandtrust.shoplex.model.enumurations.Category
import com.trueandtrust.shoplex.model.interfaces.INotifyMVP
import com.trueandtrust.shoplex.model.pojo.Product
import java.util.*
import kotlin.collections.ArrayList

class DBModel {

    private var notifier: INotifyMVP
    private val database = Firebase.firestore
    private val pendingProductsRef: CollectionReference
    private val imagesProductsRef: StorageReference

    constructor(notifier: INotifyMVP) {
        this.notifier = notifier
        this.pendingProductsRef = database.collection("Pending Products")
        this.imagesProductsRef = FirebaseStorage.getInstance().reference.child("Products")
    }

    fun addProduct(product: Product, context: Context){
        product.imageSlideList.clear()
        pendingProductsRef.document(product.productID).set(product).addOnSuccessListener {
            Toast.makeText(context, "Success: ", Toast.LENGTH_LONG).show()
        }.addOnFailureListener{
            Toast.makeText(context, "Failed: ${it.localizedMessage}", Toast.LENGTH_LONG).show()
            Log.d("FIRE", it.localizedMessage)
        }.addOnCanceledListener {
            Toast.makeText(context, "Canceled", Toast.LENGTH_LONG).show()
        }
    }

    fun addImage(uri: Uri, productID: String, position: Int) {
        val imgRef: StorageReference =
            FirebaseStorage.getInstance().reference.child("Products").child(productID).child(UUID.randomUUID().toString())

        imgRef.putFile(uri).addOnSuccessListener { _ ->
            imgRef.downloadUrl.addOnSuccessListener {
                notifier.onImageUploadedSuccess(it.toString(), position)
            }
        }.addOnFailureListener {
            notifier.onImageUploadedFailed(position)
        }


    }

    fun removeImage(path: String, context: Context){
        val imageRef = FirebaseStorage.getInstance().getReference(path)
        imageRef.delete().addOnFailureListener {
            Toast.makeText(context, "Failed to remove image", Toast.LENGTH_SHORT).show()
        }
    }

    fun getAllProducts(category: Category) : ArrayList<Product> {
        return arrayListOf(Product())
    }
}