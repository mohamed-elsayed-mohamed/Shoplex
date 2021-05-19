package com.trueandtrust.shoplex.model.pojo

import com.google.android.gms.maps.model.LatLng
import java.util.*

class Store {

    var storeID : Int? = null
    var name : String = ""
    var email : String = ""
    var location : LatLng? = null
    var phone : String = ""
    var date: Date? = null

    fun getStoreInfo(storeID : Int) : Store{

        return Store()
    }
}