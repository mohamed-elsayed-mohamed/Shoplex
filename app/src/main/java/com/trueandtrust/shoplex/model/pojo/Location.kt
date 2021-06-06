package com.trueandtrust.shoplex.model.pojo

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.TypeConverter
import com.google.gson.Gson

class Location() : Parcelable {

    var latitude: Double = 0.0
    var longitude: Double = 0.0

    constructor(parcel: Parcel) : this() {
        latitude = parcel.readDouble()
        longitude = parcel.readDouble()
    }

    constructor(latitude:Double, longitude:Double): this(){
        this.latitude = latitude
        this.longitude = longitude
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Location> {
        override fun createFromParcel(parcel: Parcel): Location {
            return Location(parcel)
        }

        override fun newArray(size: Int): Array<Location?> {
            return arrayOfNulls(size)
        }
    }
}
