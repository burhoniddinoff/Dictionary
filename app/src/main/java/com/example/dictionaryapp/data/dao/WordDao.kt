package com.example.dictionaryapp.data.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.dictionaryapp.data.model.WordData

@Dao
interface WordDao {
    @Query("SELECT * FROM dictionary")
    fun getWord(): Cursor

    @Query("SELECT * FROM dictionary WHERE english LIKE :query || '%'")
    fun getAllWordsByEn(query: String): Cursor

    @Query("SELECT * FROM dictionary WHERE uzbek LIKE :query || '%'")
    fun getAllWordsByUz(query: String): Cursor

    @Update
    fun updateData(data: WordData)

    @Query("Select * From dictionary where is_favourite = 1")
    fun cursorByFavourite(): List<WordData>

    @Query("Select * From dictionary where is_favourite = 2")
    fun cursorByFavouriteUZ(): List<WordData>
}
