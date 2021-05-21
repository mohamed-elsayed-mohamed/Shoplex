package com.trueandtrust.shoplex.model.pojo

import com.google.android.gms.maps.model.LatLng
import java.util.*
import kotlin.collections.ArrayList

class Store {

    var storeID : String = UUID.randomUUID().toString()
    var name : String = ""
    var email : String = ""
    var image : String = ""
    var locations : ArrayList<LatLng> = arrayListOf()
    var addresses : ArrayList<String> = arrayListOf()
    var phone : String? = null
    var date: Date? = null

    fun getStoreInfo(storeID : Int) : Store{

        return Store()
    }
}