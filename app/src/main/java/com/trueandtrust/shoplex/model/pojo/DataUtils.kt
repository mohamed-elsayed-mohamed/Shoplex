package com.trueandtrust.shoplex.model.pojo

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    @JvmStatic
    fun getDate(date: Date): String {
        val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm a")
        val result = sdf.format(date)
        return result
    }
}