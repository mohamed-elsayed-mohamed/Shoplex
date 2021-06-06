package com.trueandtrust.shoplex.model.extra

import android.content.Context
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.trueandtrust.shoplex.model.pojo.Location
import com.trueandtrust.shoplex.model.pojo.NotificationToken
import com.trueandtrust.shoplex.model.pojo.RecentVisit
import com.trueandtrust.shoplex.model.pojo.Store
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

object StoreInfo {
    private val SHARED_USER_INFO = "USER_INFO"
    private val NOTIFICATION = "NOTIFICATION"
    var storeID: String? = null
    var name : String = ""
    var email : String = ""
    var image : String? = null
    var locations : ArrayList<Location> = arrayListOf()
    var addresses : ArrayList<String> = arrayListOf()
    var phone : String = ""

    fun updateStoreInfo(store: Store, context: Context){
        this.storeID = store.storeID
        this.name = store.name
        this.email = store.email
        this.image = store.image
        this.locations = store.locations
        this.addresses = store.addresses
        this.phone = store.phone
        saveStoreInfo(context)
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

    fun saveStoreInfo(context: Context){
        context.getSharedPreferences(SHARED_USER_INFO, Context.MODE_PRIVATE).edit()
            .putString("storeID", storeID)
            .putString("name", name)
            .putString("email", email)
            .putString("image", image)
            .putString("phone", phone)
            .putString("locations", Gson().toJson(locations))
            .putString("addresses", Gson().toJson(addresses))
            .apply()
    }

    fun readStoreInfo(context: Context){
        val sharedPref = context.getSharedPreferences(SHARED_USER_INFO, Context.MODE_PRIVATE)
        storeID = sharedPref.getString("storeID", null)
        if(storeID == null)
            return
        name = sharedPref.getString("name", "")!!
        email = sharedPref.getString("email", "")!!
        image = sharedPref.getString("image", null)
        phone = sharedPref.getString("phone", "")!!
        val addressesType: Type = object : TypeToken<ArrayList<String>>() {}.type
        val locationsType: Type = object : TypeToken<ArrayList<Location>>() {}.type
        addresses = Gson().fromJson(sharedPref.getString("addresses", null), addressesType)
        locations = Gson().fromJson(sharedPref.getString("locations", null), locationsType)
    }

    /*
    fun setNotificationControl(context: Context, value: Boolean){
        context.getSharedPreferences(NOTIFICATION, Context.MODE_PRIVATE)
            .edit()
            .putBoolean("Notification", value).apply()
    }

    fun getNotificationControl(context: Context): Boolean{
        return context.getSharedPreferences(NOTIFICATION, Context.MODE_PRIVATE).getBoolean("Notification", true)
    }
    */

    fun saveToRecentVisits(){
        FirebaseReferences.recentVisits.add(RecentVisit())
    }

    fun clear(){
        this.storeID = null
        this.name = ""
        this.email = ""
        this.image = null
        this.locations = arrayListOf()
        this.addresses = arrayListOf()
        this.phone = ""
    }
}