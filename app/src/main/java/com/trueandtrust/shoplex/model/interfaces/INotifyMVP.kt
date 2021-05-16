package com.trueandtrust.shoplex.model.interfaces

import com.trueandtrust.shoplex.model.pojo.Property

interface INotifyMVP {
    fun onImageRemoved(position: Int){}
    fun onImageUploadedSuccess(path: String, position: Int){}
    fun onImageUploadedFailed(position: Int){}
    fun onUploadSuccess(){}
    fun onUploadFailed(){}
    fun applyData(property : Property){}
    fun onPropertyRemoved(position: Int){}
}