package com.trueandtrust.shoplex.model.pojo

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import java.io.IOException
import java.util.*


data class Location(var storeID: String = "", var storeName: String = "", var address: String, var location:LatLng) {

    companion object {
        fun getAddress(latLng: LatLng,context : Context): String {
            val geocoder = Geocoder(context, Locale.getDefault())
            var addresses: List<Address>? = null
            var address: String = " "
            try {
                addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
                if (addresses.size > 0) {
                    address = addresses[0].getAddressLine(0)
                    //Toast.makeText(this, address, Toast.LENGTH_SHORT).show()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return address
        }

    }

}