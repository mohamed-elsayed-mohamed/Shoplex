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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRightMessage(message: Message)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLeftMessage(message: Message)

    @Query("SELECT * FROM message")
    fun readAllMessage():LiveData<List<Message>>

    //product
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProduct(product: Product)

    @Query("SELECT * FROM product")
    fun readAllProducts():LiveData<List<Product>>

}