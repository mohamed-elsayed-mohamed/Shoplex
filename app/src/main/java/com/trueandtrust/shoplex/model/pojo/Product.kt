package com.trueandtrust.shoplex.model.pojo


import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.Exclude
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
    var price : Float = 0.0F
    var newPrice : Float = 0.0F
    var discount : Int = 0
    var category : String = ""
    var subCategory : String = ""
    var rate : Double? = null
    var premium : Premium? = null
    var premiumDays: Int = 0
    var properties: ArrayList<Property> = arrayListOf()
    var date: Date? = null

    var images : ArrayList<String?> = arrayListOf()

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
        parcel.readStringList(images)
        imagesListURI = parcel.readArrayList(Uri::class.java.classLoader) as ArrayList<Uri>
        properties = parcel.readArrayList(Property::class.java.classLoader) as ArrayList<Property>
    }

    @Exclude
    fun getImageSlides(): ArrayList<SlideModel>{
        this.imageSlideList.clear()
        for(image in imagesListURI){
            imageSlideList.add(SlideModel(image.toString()))
        }
        return imageSlideList
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

}