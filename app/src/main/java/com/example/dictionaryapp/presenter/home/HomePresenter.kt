package com.example.dictionaryapp.presenter.home

import android.database.Cursor
import android.util.Log
import com.example.dictionaryapp.data.model.WordData
import com.example.dictionaryapp.presenter.home.HomeContract
import com.example.dictionaryapp.presenter.home.HomeModel
import java.util.concurrent.Executors

class HomePresenter(private var view: HomeContract.View) : HomeContract.Presenter {
    private val model: HomeContract.Model = HomeModel()
    private val executors = Executors.newSingleThreadExecutor()


    override fun loadWords() {
        executors.execute {
            view.showWords(model.loadWords())
        }
    }

    override fun loadWordsByEn(query: String) {
        executors.execute {
            view.showWords(model.loadWordsByEn(query))
        }
    }

    override fun loadWordsByUz(query: String) {
        executors.execute {
            view.showWords(model.loadWordsByUz(query))
        }
    }

    override fun updateData(data: WordData) {
        executors.execute {
            model.updateData(data)
            loadWords()
        }
    }

}