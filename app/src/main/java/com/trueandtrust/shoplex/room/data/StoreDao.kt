package com.trueandtrust.shoplex.room.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.trueandtrust.shoplex.model.pojo.Message

@Dao
interface StoreDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRightMessage(message: Message)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLeftMessage(message: Message)

    @Query("SELECT * FROM message where chatId = :chatID order by messageDate")
    fun readAllMessage(chatID : String):LiveData<List<Message>>
}