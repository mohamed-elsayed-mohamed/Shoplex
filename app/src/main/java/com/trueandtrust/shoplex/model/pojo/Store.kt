package com.trueandtrust.shoplex.model.pojo

import com.google.android.gms.maps.model.LatLng

class Store {

    var storeID : Int = 0
    var name : String = ""
    lateinit var location : LatLng
    var phone : Int = 0

    fun getStoreInfo(storeID : Int) : Store{

        return Store()
    }
}