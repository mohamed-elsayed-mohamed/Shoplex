package com.trueandtrust.shoplex.model.firebase

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.toObject
import com.trueandtrust.shoplex.model.extra.FirebaseReferences
import com.trueandtrust.shoplex.model.extra.StoreInfo
import com.trueandtrust.shoplex.model.interfaces.INotifyMVP
import com.trueandtrust.shoplex.model.pojo.Store

class StoreDBModel(val notifier: INotifyMVP?) {
    fun getStoreByMail(userEmail: String) {
        FirebaseReferences.storeRef.whereEqualTo("email", userEmail).get().addOnSuccessListener {
            if(it.count() > 0) {
                val store: Store = it.documents[0].toObject()!!
                StoreInfo.updateStoreInfo(store, (notifier as AppCompatActivity).applicationContext)

                this.notifier?.onStoreInfoReady(true)
            }
            else {
                this.notifier?.onStoreInfoReady(false)
                StoreInfo.clear()
            }
        }.addOnFailureListener {
            this.notifier?.onStoreInfoFailed()
            StoreInfo.clear()
        }
    }
}