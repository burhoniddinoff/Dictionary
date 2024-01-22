package com.example.dictionaryapp.app

import android.app.Application
import com.example.dictionaryapp.data.local.MyDatabase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MyDatabase.init(this)
    }
}