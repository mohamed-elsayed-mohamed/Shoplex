package com.trueandtrust.shoplex.model.pojo


import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.databinding.Bindable
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.Exclude
import com.google.firebase.messaging.FirebaseMessaging
import com.trueandtrust.shoplex.model.enumurations.Premium
import java.util.*
import kotlin.collections.ArrayList

open class Product() : Parcelable {
    var productID : String = UUID.randomUUID().toString()
    var storeID : String = ""
    var storeName : String = ""
    var deliveryLoc: LatLng? = null
    var name : String = ""
    var description: String = ""
    var price : Float = 10F
    var newPrice : Float = 10F
    var discount : Int = 0
    var category : String = ""
    var subCategory : String = ""
    var rate : Float? = null
    var premium : Premium? = null
    var premiumDays: Int = 0
    var properties: ArrayList<Property> = arrayListOf()
    var date: Date? = null
    var tokenID: String = ""

    var images : ArrayList<String?> = arrayListOf()

    @Exclude @set:Exclude @get:Exclude
    var removedImages : ArrayList<String> = arrayListOf()

    @Exclude @set:Exclude @get:Exclude
    var imagesListURI : ArrayList<Uri> = arrayListOf()

    @Exclude @set:Exclude @get:Exclude
    var imageSlideList : ArrayList<SlideModel> = arrayListOf()

    constructor(parcel: Parcel) : this() {
        productID = parcel.readString().toString()
        name = parcel.readString().toString()
        description = parcel.readString().toString()
        price = parcel.readFloat()
        newPrice = parcel.readFloat()
        discount = parcel.readInt()
        category = parcel.readString().toString()
        subCategory = parcel.readString().toString()
        //rate = parcel.readFloat()
        premiumDays = parcel.readInt()
        parcel.readStringList(removedImages)
        tokenID = parcel.readString().toString()
        parcel.readStringList(images)
        imagesListURI = parcel.readArrayList(Uri::class.java.classLoader) as ArrayList<Uri>
        properties = parcel.readArrayList(Property::class.java.classLoader) as ArrayList<Property>
        addTokenID()
    }

    @Exclude
    fun getImageSlides(): ArrayList<SlideModel>{
        this.imageSlideList.clear()
        for(image in imagesListURI){
            imageSlideList.add(SlideModel(image.toString()))
        }
        return imageSlideList
    }

    fun calculateNewPrice(): String{
        val value = (this.price - (this.price * (this.discount / 100.0F)))
        this.newPrice = "%.2f".format(value).toFloat()
        return this.newPrice.toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(productID)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeFloat(price)
        parcel.writeFloat(newPrice)
        parcel.writeInt(discount)
        parcel.writeString(category)
        parcel.writeString(subCategory)
        //rate?.let { parcel.writeFloat(it) }
        parcel.writeInt(premiumDays)
        parcel.writeStringList(removedImages)
        parcel.writeString(tokenID)
        parcel.writeStringList(images)
        parcel.writeArray(imagesListURI.toArray())
        parcel.writeArray(properties.toArray())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }

    fun addTokenID(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }

            val token = task.result
            this.tokenID = token.toString()

            //FirebaseReferences.pendingProductsRef.document(product.productID).update("tokenID", token)
        })
    }
}