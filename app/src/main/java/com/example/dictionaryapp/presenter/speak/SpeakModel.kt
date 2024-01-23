package com.example.dictionaryapp.presenter.speak

import android.database.Cursor
import com.example.dictionaryapp.domain.AppRepository
import com.example.dictionaryapp.domain.AppRepositoryImpl

class SpeakModel : SpeakContract.Model {

    private val repository: AppRepository = AppRepositoryImpl.getInstance()

    override fun loadWordsByEn(query: String): Cursor = repository.getAllWordsByEn(query)
    override fun loadWordsByUz(query: String): Cursor = repository.getAllWordsByUz(query)

}