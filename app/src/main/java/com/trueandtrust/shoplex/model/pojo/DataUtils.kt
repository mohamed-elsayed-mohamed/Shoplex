package com.trueandtrust.shoplex.model.pojo

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    @JvmStatic
    fun getDate(date: Date): String = SimpleDateFormat("dd-MM-yyyy hh:mm a").format(date)
}