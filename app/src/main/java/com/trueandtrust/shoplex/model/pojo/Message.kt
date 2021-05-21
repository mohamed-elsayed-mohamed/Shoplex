package com.trueandtrust.shoplex.model.pojo

import com.google.firebase.Timestamp
import java.util.*

data class Message(var messageID : String = Timestamp.now().toDate().time.toString(), val messageDate: Date = Timestamp.now().toDate(), val toId : String = "", val message: String = "") {


}
