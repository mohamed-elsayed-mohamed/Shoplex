package com.trueandtrust.shoplex.model.pojo

import com.google.firebase.Timestamp
import java.util.*

data class Message(var messageID : String = UUID.randomUUID().toString(), val messageDate: String = Timestamp.now().toDate().time.toString(), val toId : String = "0", val message: String = "") {


}
