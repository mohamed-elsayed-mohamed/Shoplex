package com.trueandtrust.shoplex.model.firebase

import android.content.Context
import android.net.Uri
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.trueandtrust.shoplex.model.extra.FirebaseReferences
import com.trueandtrust.shoplex.model.extra.StoreInfo
import com.trueandtrust.shoplex.model.interfaces.ProductsListener
import com.trueandtrust.shoplex.model.pojo.Location
import com.trueandtrust.shoplex.model.pojo.Product
import com.trueandtrust.shoplex.model.pojo.Review
import com.trueandtrust.shoplex.model.pojo.ReviewStatistics
import java.util.*

class ProductsDBModel(val listener: ProductsListener?) {
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
        product.storeLocation = StoreInfo.locations.firstOrNull() ?: Location()
        reference.document(product.productID).set(product).addOnSuccessListener {
            listener?.onProductAdded()
        }

        for (imgURI in product.imagesListURI)
            if (!product.images.contains(imgURI.path))
                addImage(imgURI)

        for (imgURL in product.removedImages)
            deleteImage(imgURL)
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
        imageRef.delete()
    }

    fun getAllProducts() {
        FirebaseReferences.productsRef.whereEqualTo("storeID", StoreInfo.storeID)
            .whereEqualTo("deleted", false)
            .addSnapshotListener { values, _ ->
                val products = arrayListOf<Product>()
                for (document: DocumentSnapshot in values?.documents!!) {
                    val product: Product? = document.toObject<Product>()
                    if (product != null) {
                        products.add(product)
                    }

                    if (document == values.documents.last()) {
                        this.listener?.onAllProductsReady(products)
                    }
                }
            }
    }

    fun deleteProduct(productID: String) {
        FirebaseReferences.productsRef.document(productID).update("deleted", true)
            .addOnSuccessListener {
                this.listener?.onProductRemoved()
            }
    }

    fun getReviewByProductId(productId: String) {

        FirebaseReferences.productsRef.document(productId).collection("Reviews")
            .addSnapshotListener { values, _ ->
                val reviews = arrayListOf<Review>()
                for (document in values?.documents!!) {
                    val review: Review? = document.toObject<Review>()
                    if (review != null) {
                        reviews.add(review)
                    }
                }
                this.listener?.onAllReviewsReady(reviews)
            }
    }

    fun getReviewsStatistics(productId: String) {
        FirebaseReferences.productsRef.document(productId).collection("Statistics")
            .document("Reviews").get().addOnSuccessListener {
            if (it.exists()) {
                val statistic: ReviewStatistics = it.toObject()!!
                this.listener?.onReviewStatisticsReady(statistic)
            }

        }
    }
}