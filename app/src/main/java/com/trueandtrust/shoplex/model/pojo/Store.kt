package com.trueandtrust.shoplex.model.pojo

import java.util.*
import kotlin.collections.ArrayList

class Store {
    var storeID : String = UUID.randomUUID().toString()
    var name : String = ""
    var email : String = ""
    var image : String = ""
    var locations : ArrayList<Location> = arrayListOf()
    var addresses : ArrayList<String> = arrayListOf()
    var phone : String = ""
    var date: Date? = null
}