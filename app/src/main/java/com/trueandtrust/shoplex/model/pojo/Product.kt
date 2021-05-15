package com.trueandtrust.shoplex.model.pojo


import android.net.Uri
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.gms.maps.model.LatLng
import com.trueandtrust.shoplex.model.enumurations.Category
import com.trueandtrust.shoplex.model.enumurations.Premium

import com.trueandtrust.shoplex.model.enumurations.SubFashion


open class Product {
    var productID : Int = 0
    var storeID : Int = 0
    var storeName : String = ""
    open var name : String = ""
    var description: String = ""
    open var price : Float = 0.0F
    var newPrice : Float = 0.0F
    var oldPrice : Float = 0.0F
    var sold:String=""
    var discount : Int = 0
    open var category : Category = Category.FASHION
    var images : ArrayList<Uri> = ArrayList()
    val imageSlideList = ArrayList<SlideModel>()
    var subCategory : SubFashion = SubFashion.MEN
    var rate : Double = 0.0
    var permium : Premium = Premium.BASIC
    var deliveryLoc: LatLng = LatLng(0.0, 0.0)
    open var productImageUrl : String = ""



    constructor()
    constructor(
        name: String,
        price: Float,
        category: Category,
        deliveryLoc: LatLng,
        productImageUrl: String
    ) {
        this.name = name
        this.price = price
        this.category = category
        this.deliveryLoc = deliveryLoc
        this.productImageUrl = productImageUrl
    }

    constructor(
        name: String,
        newPrice: Float,
        oldPrice: Float,
        sold: String,
        rate: Double,
        productImageUrl: String
    ) {
        this.name = name
        this.newPrice = newPrice
        this.oldPrice = oldPrice
        this.sold = sold
        this.rate = rate
        this.productImageUrl = productImageUrl
    }

    fun getAllProducts(category: Category) : ArrayList<Product> {
        return arrayListOf(Product())
    }

    fun addProduct(product: Product) :Boolean{
        return  true
    }

}