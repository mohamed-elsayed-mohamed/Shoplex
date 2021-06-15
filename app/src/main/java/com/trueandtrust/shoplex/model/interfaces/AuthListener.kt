package com.trueandtrust.shoplex.model.interfaces

import android.content.Context
import com.trueandtrust.shoplex.model.extra.StoreInfo
import com.trueandtrust.shoplex.model.pojo.Store

interface AuthListener {
    fun showIndicator(){}
    fun hideIndicator(){}

    private fun onStoreInfoReady(context: Context, store: Store){
        StoreInfo.storeID = store.storeID
        StoreInfo.name = store.name
        StoreInfo.email = store.email
        StoreInfo.image = store.image
        StoreInfo.locations = store.locations
        StoreInfo.addresses = store.addresses
        StoreInfo.phone = store.phone
        StoreInfo.updateTokenID()
        StoreInfo.saveStoreInfo(context)
        StoreInfo.saveToRecentVisits()
        StoreInfo.saveNotification(context, true)
    }

    fun onPendingStore(){

    }

    fun onLoginSuccess(context: Context, store: Store){
        onStoreInfoReady(context, store)
    }

    fun onLoginFailed(){
        StoreInfo.clear()
    }

    fun onAddStoreFailed(){
    }
}