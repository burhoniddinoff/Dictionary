package com.example.dictionaryapp.domain

import android.database.Cursor
import com.example.dictionaryapp.data.model.WordData

interface AppRepository {
    fun getAllWord() : Cursor
    fun getAllWordsByEn(query: String) : Cursor
    fun getAllWordsByUz(query: String) : Cursor
    fun updateData(data: WordData)
    fun cursorByFavourite(): List<WordData>
    fun cursorByFavouriteUz(): List<WordData>
}