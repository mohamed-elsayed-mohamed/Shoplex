package com.trueandtrust.shoplex.model.interfaces

interface INotifyMVP {
    fun onImageRemoved(position: Int){}
    fun onUploadSuccess(){}
    fun onUploadFailed(){}
}