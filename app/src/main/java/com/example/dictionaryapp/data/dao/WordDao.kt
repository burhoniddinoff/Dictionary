package com.example.dictionaryapp.data.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Query

@Dao
interface WordDao {
    @Query("SELECT * FROM dictionary")
    fun getWord(): Cursor

    @Query("SELECT * FROM dictionary WHERE english LIKE :query || '%'")
    fun getAllWordsByEn(query: String) : Cursor
}
