package com.example.dictionaryapp.presenter.saved

import com.example.dictionaryapp.data.model.WordData

interface SavedContract {
    interface Model {
        fun getCursorFromRepo(): List<WordData>
        fun getUzListFromRepo(): List<WordData>
    }

    interface View {
        fun cursor(cursor: List<WordData>)
    }

    interface Presenter {
        fun loadCursor()
        fun loadUzList()
    }
}
