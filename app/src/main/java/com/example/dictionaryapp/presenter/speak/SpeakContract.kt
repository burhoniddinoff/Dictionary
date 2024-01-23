package com.example.dictionaryapp.presenter.speak

import android.content.Intent
import android.database.Cursor

interface SpeakContract {

    interface Model {
        fun loadWordsByEn(query: String): Cursor
        fun loadWordsByUz(query: String): Cursor
    }

    interface View {
        fun updateResults(s: String, translation: String)
        fun promptSpeechInput(isUzbek: Boolean, reqCode: Int)
    }

    interface Presenter {
        fun onEnglishButtonClicked()
        fun onUzbekButtonClicked()
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    }

}