package com.trueandtrust.shoplex.model.interfaces

import com.trueandtrust.shoplex.model.pojo.*
import kotlin.collections.ArrayList

interface INotifyMVP {
    fun onImageRemoved(position: Int){}
    fun onImageUploadedSuccess(path: String, position: Int){}
    fun onImageUploadedFailed(position: Int){}
    fun onUploadSuccess(){}
    fun onUploadFailed(){}
    fun onNewPropertyAdded(property: Property){}
    fun onPropertyRemoved(position: Int){}



    fun onStoreInfoReady(isAccountActive: Boolean){}
    fun onStoreInfoFailed(){}



}