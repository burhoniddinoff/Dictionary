package com.example.dictionaryapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dictionaryapp.data.dao.WordDao
import com.example.dictionaryapp.data.model.WordData

@Database(entities = [WordData::class], version = 1)
abstract class MyDatabase : RoomDatabase() {

    abstract fun dao(): WordDao

    companion object {
        @Volatile
        private lateinit var instance: MyDatabase

        fun init(context: Context) {
            if (!(::instance.isInitialized)) {
                synchronized(lock = "") {
                    instance =
                        Room.databaseBuilder(context, MyDatabase::class.java, "my_dictionary.db")
                            .createFromAsset("dictionary.db")
//                            .allowMainThreadQueries()
                            .build()
                }
            }

        }

        fun getInstance() = instance
    }
}