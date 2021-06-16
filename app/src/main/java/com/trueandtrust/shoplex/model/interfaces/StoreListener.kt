package com.trueandtrust.shoplex.model.interfaces

import com.trueandtrust.shoplex.model.pojo.Location

interface StoreListener {
    fun onAllAddressesReady(addresses: ArrayList<String>, locations: ArrayList<Location>){}

    fun onRemoveLocationSuccess(){}
}
