package com.trueandtrust.shoplex.model

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.trueandtrust.shoplex.model.enumurations.Category
import com.trueandtrust.shoplex.model.pojo.Product
import java.util.*

class DBModel {
    private var product: Product
    private var context: Context
    private val database = Firebase.firestore
    private val pendingProductsRef: CollectionReference
    private val productsRef: CollectionReference
    private val imagesProductsRef: StorageReference
    val reference: CollectionReference

    constructor(product: Product, context: Context, isUpdate: Boolean) {
        this.product = product
        this.context = context
        this.pendingProductsRef = database.collection("Pending Products")
        this.productsRef = database.collection("Products")
        this.imagesProductsRef = FirebaseStorage.getInstance().reference.child("Products")
        reference = if(isUpdate) productsRef else pendingProductsRef
    }

    fun addProduct() {
        reference.document(product.productID).set(product).addOnSuccessListener {
            Toast.makeText(context, "Success: ", Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            Toast.makeText(context, "Failed: ${it.localizedMessage}", Toast.LENGTH_LONG).show()
            Log.d("FIRE", it.localizedMessage)
        }

        for (imgURI in product.imagesListURI)
            if (!product.images.contains(imgURI.path))
                addImage(imgURI)

        for (imgURL in product.removedImages) {
            removeImage(imgURL)
        }
    }

    private fun addImage(uri: Uri) {
        val imgRef: StorageReference =
            imagesProductsRef.child(product.productID).child(UUID.randomUUID().toString())

        imgRef.putFile(uri).addOnSuccessListener { _ ->
            imgRef.downloadUrl.addOnSuccessListener {
                //Toast.makeText(context, "Uploaded: ", Toast.LENGTH_LONG).show()
                reference.document(product.productID).update("images", FieldValue.arrayUnion(it.toString()))
               // notifier.onImageUploadedSuccess(it.toString(), position)
            }
        }.addOnFailureListener {
           // notifier.onImageUploadedFailed(position)
        }
    }

    private fun updateProduct(){
        this.addProduct()

    }

    fun removeImage(path: String) {
        //productsRef.document(product.productID).update("images", product.images)
        val imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(path)
        imageRef.delete().addOnFailureListener {
            Toast.makeText(context, "Failed to remove image", Toast.LENGTH_SHORT).show()
        }
    }

    fun getAllProducts(category: Category) : ArrayList<Product> {
        return arrayListOf(Product())
    }
}