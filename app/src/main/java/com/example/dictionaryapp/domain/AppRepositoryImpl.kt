package com.example.dictionaryapp.domain

import android.database.Cursor
import com.example.dictionaryapp.data.local.MyDatabase
import com.example.dictionaryapp.data.model.WordData

class AppRepositoryImpl : AppRepository {
    private var wordDao = MyDatabase.getInstance().dao()

    companion object {
        private lateinit var instance: AppRepository

        fun getInstance(): AppRepository {
            if (!(Companion::instance.isInitialized)) instance = AppRepositoryImpl()
            return instance
        }
    }


    override fun getAllWord(): Cursor = wordDao.getWord()
    override fun getAllWordsByEn(query: String): Cursor = wordDao.getAllWordsByEn(query)
    override fun getAllWordsByUz(query: String): Cursor = wordDao.getAllWordsByUz(query)
    override fun updateData(data: WordData) = wordDao.updateData(data)
    override fun cursorByFavourite(): List<WordData> = wordDao.cursorByFavourite()
    override fun cursorByFavouriteUz(): List<WordData> = wordDao.cursorByFavouriteUZ()

    override fun getAllBookmarks(query: Int): Cursor = wordDao.getBookmarks(query)
}