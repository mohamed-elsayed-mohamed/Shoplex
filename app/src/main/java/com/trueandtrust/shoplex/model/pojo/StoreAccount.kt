package com.trueandtrust.shoplex.model.pojo

import com.google.android.gms.maps.model.LatLng
import com.trueandtrust.shoplex.model.enumurations.Language

class StoreAccount {

    var enablesNotification : Boolean = false

    fun chngeLanguage(language: Language) {

    }
    fun addStoreLocation(storeLocation : LatLng) : Boolean{
        return true
    }
}