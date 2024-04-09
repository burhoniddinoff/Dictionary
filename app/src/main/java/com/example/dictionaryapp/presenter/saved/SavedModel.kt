package com.example.dictionaryapp.presenter.saved

import com.example.dictionaryapp.data.model.WordData
import com.example.dictionaryapp.domain.AppRepository
import com.example.dictionaryapp.domain.AppRepositoryImpl

class SavedModel : SavedContract.Model {
    private val repository: AppRepository = AppRepositoryImpl.getInstance()

    override fun getCursorFromRepo(): List<WordData> {
        return repository.cursorByFavourite()
    }

    override fun getUzListFromRepo(): List<WordData> {
        return repository.cursorByFavouriteUz()
    }

}