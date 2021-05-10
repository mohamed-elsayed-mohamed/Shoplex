package com.trueandtrust.shoplex.model.pojo


import com.trueandtrust.shoplex.model.enumurations.Category
import com.trueandtrust.shoplex.model.enumurations.Permium
import com.trueandtrust.shoplex.model.enumurations.SubFashion

open class Product {
    var productID : Int = 0
    var storeID : Int = 0
    var storeName : String = ""
    var name : String = ""
    var price : Double = 0.0
    var newPrice : Double = 0.0
    var discount : Int = 0
    var category : Category = Category.FASHION
    var subCategory : SubFashion = SubFashion.MEN
    lateinit var image : String //array
    var rate : Double = 0.0
    var permium : Permium = Permium.BASIC

    fun getAllProducts(category: Category) : ArrayList<Product> {
        return arrayListOf(Product())
    }

    fun addProduct(product: Product) :Boolean{
        return  true
    }

}