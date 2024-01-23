package com.example.dictionaryapp.domain

import android.database.Cursor

interface AppRepository {
    fun getAllWord() : Cursor
    fun getAllWordsByEn(query: String) : Cursor
    fun getAllWordsByUz(query: String) : Cursor
}