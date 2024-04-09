package com.example.dictionaryapp.presenter.saved

import java.util.concurrent.Executors

class SavedPresenter(var view: SavedContract.View) : SavedContract.Presenter {
    private val model: SavedContract.Model = SavedModel()
    private val executors = Executors.newSingleThreadExecutor()


    override fun loadCursor() {
        executors.execute {
            view.cursor(model.getCursorFromRepo())
        }
    }

    override fun loadUzList() {
        executors.execute {
            view.cursor(model.getUzListFromRepo())
        }
    }

}