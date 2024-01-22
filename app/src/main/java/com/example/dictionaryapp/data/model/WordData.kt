package com.example.dictionaryapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dictionary")
data class WordData(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val english: String,
    val type: String,
    val transcript: String,
    val uzbek: String,
    val countable: String,
    val is_favourite: Int
)
