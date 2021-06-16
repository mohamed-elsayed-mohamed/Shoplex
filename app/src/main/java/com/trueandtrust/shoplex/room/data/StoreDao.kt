package com.trueandtrust.shoplex.room.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.trueandtrust.shoplex.model.pojo.Message
import com.trueandtrust.shoplex.model.pojo.Product

@Dao
interface StoreDao {

    //message
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMessage(message: Message)

    @Query("SELECT * FROM message where chatID = :chatID order by messageID")
    fun readAllMessage(chatID : String): LiveData<List<Message>>

    @Query("UPDATE message SET isRead = 1 where messageID = :messageID")
    fun setReadMessage(messageID : String)

    //product
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProduct(product: Product)

    @Query("SELECT * FROM product")
    fun readAllProducts():LiveData<List<Product>>

}