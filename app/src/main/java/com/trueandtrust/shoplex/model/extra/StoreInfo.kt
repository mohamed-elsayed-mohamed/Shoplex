package com.trueandtrust.shoplex.model.extra

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.trueandtrust.shoplex.model.pojo.Loc
import com.trueandtrust.shoplex.model.pojo.NotificationToken
import com.trueandtrust.shoplex.model.pojo.Store
import java.util.*
import kotlin.collections.ArrayList

object StoreInfo {
    var storeID: String? = null
    var name : String = ""
    var email : String = ""
    var image : String? = null
    var locations : ArrayList<Loc> = arrayListOf()
    var addresses : ArrayList<String> = arrayListOf()
    var phone : String? = null

    fun updateStoreInfo(store: Store){
        this.storeID = store.storeID
        this.name = store.name
        this.email = store.email
        this.image = store.image
        this.phone = store.phone
        this.addresses = store.addresses
        this.locations = store.locations
    }

    fun updateTokenID(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }

            val token = task.result
            val notificationToken = NotificationToken(this.storeID!!, token.toString())
            FirebaseReferences.notificationTokensRef.document(this.storeID!!).set(notificationToken)
        })
    }

    fun clear(){
        this.storeID = null
        this.name = ""
        this.email = ""
        this.image = null
        this.locations = arrayListOf()
        this.addresses = arrayListOf()
        this.phone = null
    }
}