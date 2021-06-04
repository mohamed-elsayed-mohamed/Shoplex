package com.trueandtrust.shoplex.model.pojo

import android.annotation.SuppressLint
import com.google.firebase.Timestamp
import java.util.*

data class Message(var messageID : String = Timestamp.now().toDate().time.toString(), var messageDate: Date = Timestamp.now().toDate(), val toId : String = "", var message: String = "", @field:JvmField val isSent: Boolean = false, @field:JvmField val isRead: Boolean = false)
