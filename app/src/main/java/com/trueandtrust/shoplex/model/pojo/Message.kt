package com.trueandtrust.shoplex.model.pojo

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "message")
data class Message(
    var messageID: String = Timestamp.now().toDate().time.toString(),
    var messageDate: Date = Timestamp.now().toDate(),
    val toId: String? = "",
    var message: String = "",
    @field:JvmField val isSent: Boolean = false,
    @field:JvmField val isRead: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0
):Parcelable