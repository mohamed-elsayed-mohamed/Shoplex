package com.trueandtrust.shoplex.model.extra

import android.app.Application
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.firebase.firestore.FieldValue

class ArchLifecycleApp : Application(), LifecycleObserver {

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onAppCreated() {
        //FirebaseReferences.sellersDashboardDoc.update("new", FieldValue.increment(5))
        //FirebaseReferences.sellersDashboardDoc.update("deleted", FieldValue.increment(1))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        if (StoreInfo.storeID != null) {
            FirebaseReferences.storeRef.document(StoreInfo.storeID!!).update("isOnline", true)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        if (StoreInfo.storeID != null)
            FirebaseReferences.storeRef.document(StoreInfo.storeID!!).update("isOnline", false)
    }
}