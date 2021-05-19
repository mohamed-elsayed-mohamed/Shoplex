package com.trueandtrust.shoplex.model.pojo

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.PropertyName

class Property() : Parcelable {
    var name : String = ""
    var values: ArrayList<String> = arrayListOf()

    constructor(parcel: Parcel) : this() {
        name = parcel.readString().toString()
        parcel.readStringList(values)
    }

    constructor(name: String, values: ArrayList<String>) : this() {
        this.name = name
        this.values = values
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeStringList(values)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Property> {
        override fun createFromParcel(parcel: Parcel): Property {
            return Property(parcel)
        }

        override fun newArray(size: Int): Array<Property?> {
            return arrayOfNulls(size)
        }
    }
}