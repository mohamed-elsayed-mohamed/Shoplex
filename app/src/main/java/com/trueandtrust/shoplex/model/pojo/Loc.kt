package com.trueandtrust.shoplex.model.pojo

import com.google.android.gms.maps.model.LatLng

class Loc {
    var latitude: Double = 0.0
    var longitude: Double = 0.0

    constructor(){}
    constructor(latitude:Double, longitude:Double){
        this.latitude = latitude
        this.longitude = longitude
    }
}