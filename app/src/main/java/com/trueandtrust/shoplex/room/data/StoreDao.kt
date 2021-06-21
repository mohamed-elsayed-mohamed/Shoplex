package com.trueandtrust.shoplex.room.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.trueandtrust.shoplex.model.enumurations.OrderStatus
import com.trueandtrust.shoplex.model.pojo.Message
import com.trueandtrust.shoplex.model.pojo.Order
import com.trueandtrust.shoplex.model.pojo.Product

@Dao
interface StoreDao {

    //message
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMessage(message: Message)

    @Query("SELECT * FROM message where chatID = :chatID order by messageID")
    fun readAllMessage(chatID : String): LiveData<List<Message>>

    @Query("UPDATE message SET isSent = 1 where messageID = :messageID")
    fun setSent(messageID : String)

    @Query("UPDATE message SET isRead = 1 where messageID = :messageID")
    fun setReadMessage(messageID : String)

    //product
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)

    @Query("SELECT * FROM product")
    fun readAllProducts():LiveData<List<Product>>

    //last order
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLasOrder(lastOrder: Order)

    @Query("SELECT * FROM orders WHERE orderStatus IN(:orderDelivered,:orderCanceled)")
    fun readAllLastOrder(orderDelivered :OrderStatus ,orderCanceled: OrderStatus):LiveData<List<Order>>
}