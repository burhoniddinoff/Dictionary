package com.example.dictionaryapp.domain

import android.database.Cursor
import com.example.dictionaryapp.data.local.MyDatabase

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
}