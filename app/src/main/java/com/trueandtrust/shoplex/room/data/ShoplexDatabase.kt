package com.trueandtrust.shoplex.room.data

import android.content.Context
import androidx.room.*
import com.trueandtrust.shoplex.model.pojo.Message
import com.trueandtrust.shoplex.model.pojo.Product

@Database(entities = [Message::class, Product::class],version = 2 ,exportSchema = false)
@TypeConverters(Conventers::class)
abstract class ShoplexDatabase : RoomDatabase() {

    abstract fun storeDao() : StoreDao

    companion object{
        @Volatile
        private var INSTANCE : ShoplexDatabase? = null
        fun getDatabase(context: Context):ShoplexDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShoplexDatabase::class.java,
                    "Shoplex_Store_Database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}