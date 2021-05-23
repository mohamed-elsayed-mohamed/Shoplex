package com.trueandtrust.shoplex.model.pojo

class NotificationToken {
    var userID: String = ""
    var tokenID: String = ""
    var userType: String = "Store"

    constructor(userID: String, tokenID: String) {
        this.userID = userID
        this.tokenID = tokenID
    }
}