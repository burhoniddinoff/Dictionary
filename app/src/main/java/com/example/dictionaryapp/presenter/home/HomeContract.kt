package com.example.dictionaryapp.presenter.home

import android.database.Cursor

interface HomeContract {

    interface Model {
        fun loadWords(): Cursor
        fun loadWordsByEn(query: String): Cursor
    }

    interface View {
        fun showWords(cursor: Cursor)
    }

    interface Presenter {
        fun loadWords()
        fun loadWordsByEn(query: String)
    }

}