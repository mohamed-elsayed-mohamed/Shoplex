package com.trueandtrust.shoplex.model.firebase

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.model.extra.FirebaseReferences
import com.trueandtrust.shoplex.model.interfaces.INotifyMVP
import com.trueandtrust.shoplex.model.pojo.Product
import com.trueandtrust.shoplex.model.pojo.Review
import java.util.*
import kotlin.collections.ArrayList

class ProductsDBModel(val notifier: INotifyMVP?) {
    private lateinit var product: Product
    private lateinit var context: Context

    private lateinit var reference: CollectionReference

    constructor(product: Product, context: Context, isUpdate: Boolean) : this(null) {
        this.product = product
        this.context = context

        reference =
            if (isUpdate) FirebaseReferences.productsRef else FirebaseReferences.pendingProductsRef
    }

    fun addProduct() {
        reference.document(product.productID).set(product).addOnSuccessListener {
            Toast.makeText(context, R.string.Success.toString() , Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            Toast.makeText(context, context.getString(R.string.Failed) + it.localizedMessage, Toast.LENGTH_LONG).show()
            Log.d("FIRE", it.localizedMessage)
        }

        for (imgURI in product.imagesListURI)
            if (!product.images.contains(imgURI.path))
                addImage(imgURI)

        for (imgURL in product.removedImages) {
            deleteImage(imgURL)
        }
    }

    private fun addImage(uri: Uri) {
        val imgRef: StorageReference =
            FirebaseReferences.imagesProductsRef.child(product.productID)
                .child(UUID.randomUUID().toString())

        imgRef.putFile(uri).addOnSuccessListener { _ ->
            imgRef.downloadUrl.addOnSuccessListener {
                reference.document(product.productID)
                    .update("images", FieldValue.arrayUnion(it.toString()))
            }
        }
    }

    private fun deleteImage(path: String) {
        val imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(path)
        imageRef.delete().addOnFailureListener {
            Toast.makeText(context, R.string.Failed.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    fun getAllProducts(storeID: String) {
        FirebaseReferences.productsRef.whereEqualTo("storeID", storeID)
            .addSnapshotListener { values, _ ->
                var products = arrayListOf<Product>()
                for (document: DocumentSnapshot in values?.documents!!) {
                    var product: Product? = document.toObject<Product>()
                    if (product != null) {
                        products.add(product)
                    }

                    if (document == values.documents.last()) {
                        this.notifier?.onAllProductsReady(products)
                    }
                }
            }
    }
    fun getProductById(productId: String) {

        FirebaseReferences.productsRef.whereEqualTo("productID", productId)
            .addSnapshotListener { values, _ ->
                var products = arrayListOf<Product>()
                for (document: DocumentSnapshot in values?.documents!!) {
                    var product: Product? = document.toObject<Product>()
                    if (product != null) {
                        products.add(product)
                    }
                }
                this.notifier?.onAllProductsReady(products)
            }
    }
    fun deleteProduct(productID: String, images: ArrayList<String?>){
        FirebaseReferences.productsRef.document(productID).delete().addOnSuccessListener {
            this.notifier?.onProductRemoved()
            for (imgURL in images){
                FirebaseStorage.getInstance().getReferenceFromUrl(imgURL!!).delete()
            }
        }
    }
    fun getReviewByProductId(productId: String) {

        FirebaseReferences.productsRef.document(productId).collection("Reviews")
            .addSnapshotListener  { values,_ ->
                var reviews = arrayListOf<Review>()
                for (document in values?.documents!!) {
                    var review: Review? = document.toObject<Review>()
                    if (review != null) {
                        reviews.add(review)
                    }
                }
                this.notifier?.onAllReviwsReady(reviews)
            }
    }
}