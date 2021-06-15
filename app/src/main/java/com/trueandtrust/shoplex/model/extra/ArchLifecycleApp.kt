package com.trueandtrust.shoplex.model.extra

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner

class ArchLifecycleApp : Application(), LifecycleObserver {

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        if (StoreInfo.storeID != null) {
            FirebaseReferences.chatRef.whereEqualTo("storeID", StoreInfo.storeID!!).get().addOnSuccessListener {
                for(ref in it.documents)
                    ref.reference.update("isOnline", true)
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        if (StoreInfo.storeID != null) {
            FirebaseReferences.chatRef.whereEqualTo("storeID", StoreInfo.storeID!!).get().addOnSuccessListener {
                for(ref in it.documents)
                    ref.reference.update("isOnline", false)
            }
        }
    }
}