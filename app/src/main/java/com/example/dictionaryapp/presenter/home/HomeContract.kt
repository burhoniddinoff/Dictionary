package com.example.dictionaryapp.presenter.home

import android.database.Cursor
import com.example.dictionaryapp.data.model.WordData

interface HomeContract {

    interface Model {
        fun loadWords(): Cursor
        fun loadWordsByEn(query: String): Cursor
        fun loadWordsByUz(query: String): Cursor
        fun updateData(data: WordData)
    }

    interface View {
        fun showWords(cursor: Cursor)
    }

    interface Presenter {
        fun loadWords()
        fun loadWordsByEn(query: String)
        fun loadWordsByUz(query: String)
        fun updateData(data: WordData)
    }

}