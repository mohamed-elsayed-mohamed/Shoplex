package com.trueandtrust.shoplex.room.data

import androidx.room.TypeConverter
import java.util.*

class Conventers {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

}