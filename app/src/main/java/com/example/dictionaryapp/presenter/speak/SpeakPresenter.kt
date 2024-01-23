package com.example.dictionaryapp.presenter.speak

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent

class SpeakPresenter(private val view: SpeakContract.View) : SpeakContract.Presenter {

    private val reqCode = 100
    private var isUzbek = false
    private val model: SpeakContract.Model = SpeakModel()

    override fun onEnglishButtonClicked() {
        isUzbek = false
        view.promptSpeechInput(this.isUzbek, reqCode)
    }

    override fun onUzbekButtonClicked() {
        isUzbek = true
        view.promptSpeechInput(this.isUzbek, reqCode)
    }

    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == reqCode) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val message = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                val usage = message?.get(0) ?: ""
                val translationCursor = if (isUzbek) {
                    model.loadWordsByUz(usage)
                } else model.loadWordsByEn(usage)
                if (isUzbek) {
                    translationCursor.let {
                        if (it.moveToPosition(0)) {
                            val english = it.getString(it.getColumnIndex("english"))
                            val uzbek = it.getString(it.getColumnIndex("uzbek"))
                            view.updateResults(uzbek, english)
                        }
                    }
                } else {
                    translationCursor.let {
                        if (it.moveToPosition(0)) {
                            val english = it.getString(it.getColumnIndex("english"))
                            val uzbek = it.getString(it.getColumnIndex("uzbek"))
                            view.updateResults(english, uzbek)
                        }
                    }
                }
            }
        }
    }


}
