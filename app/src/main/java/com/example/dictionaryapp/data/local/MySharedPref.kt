package com.example.dictionaryapp.data.local

import android.content.Context
import android.content.SharedPreferences

object MySharedPref {

    private lateinit var sharedPreferences: SharedPreferences

    fun getInstance(context: Context): MySharedPref {
        if (!MySharedPref::sharedPreferences.isInitialized) {
            sharedPreferences = context.getSharedPreferences("abc", Context.MODE_PRIVATE)
        }
        return this
    }

    fun saveOptions(int: Int) {
        sharedPreferences.edit().putInt("Open", int).apply()
    }

    fun getOpenScreen() :Int {
        return sharedPreferences.getInt("Open", 0)
    }

}