package com.trueandtrust.shoplex.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trueandtrust.shoplex.model.firebase.StoreDBModel
import com.trueandtrust.shoplex.model.interfaces.StoreListener
import com.trueandtrust.shoplex.model.pojo.Location
import com.trueandtrust.shoplex.model.pojo.PendingLocation

class StoreVM: ViewModel(), StoreListener {

    private var storeDBModel: StoreDBModel = StoreDBModel(this)
    var storeAddresses: MutableLiveData<ArrayList<PendingLocation>> = MutableLiveData()
    var isLocationRemoved: MutableLiveData<Boolean> = MutableLiveData()

    fun getAllAddresses(){
        storeDBModel.getStoreAddresses()
    }

    fun addStoreLocation(pendingLocation: PendingLocation){
        storeDBModel.addStoreLocation(pendingLocation)
    }

    fun removeLocationAddress(address: String, location: Location){

    }

    override fun onAllAddressesReady(addresses: ArrayList<String>, locations:ArrayList<Location>) {

      //  storeAddresses.value = addresses
    }

    override fun onRemoveLocationSuccess() {
        isLocationRemoved.value = true
    }
}