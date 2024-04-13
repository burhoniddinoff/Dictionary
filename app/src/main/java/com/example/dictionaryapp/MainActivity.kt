package com.example.dictionaryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dictionaryapp.presenter.main.MainScreen
import com.example.dictionaryapp.utils.replaceScreenWithoutSave

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null)
            replaceScreenWithoutSave(MainScreen())


    }
}