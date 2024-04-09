package com.example.dictionaryapp.presenter.home

import android.database.Cursor
import com.example.dictionaryapp.data.model.WordData
import com.example.dictionaryapp.domain.AppRepository
import com.example.dictionaryapp.domain.AppRepositoryImpl
import com.example.dictionaryapp.presenter.home.HomeContract

class HomeModel : HomeContract.Model {


    private val repository: AppRepository = AppRepositoryImpl.getInstance()

    override fun loadWords(): Cursor = repository.getAllWord()
    override fun loadWordsByEn(query: String): Cursor = repository.getAllWordsByEn(query)
    override fun loadWordsByUz(query: String): Cursor = repository.getAllWordsByUz(query)
    override fun updateData(data: WordData) = repository.updateData(data)

}