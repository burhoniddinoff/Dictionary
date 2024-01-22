package com.example.dictionaryapp.presenter.home

import android.database.Cursor
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionaryapp.R
import com.example.dictionaryapp.databinding.ScreenHomeBinding
import com.example.dictionaryapp.presenter.adapter.WordAdapter

class HomeScreen : Fragment(R.layout.screen_home), HomeContract.View {
    private val binding by viewBinding(ScreenHomeBinding::bind)
    private lateinit var adapter: WordAdapter
    private val presenter: HomeContract.Presenter by lazy { HomePresenter(this) }
    private var currentQuery: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        adapter = WordAdapter()
        binding.recycler.adapter = adapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                currentQuery = newText
                if (currentQuery == null) presenter.loadWords()
                else presenter.loadWordsByEn(currentQuery!!)

                return true
            }
        })

        val closeButton = binding.searchView.findViewById(androidx.appcompat.R.id.search_close_btn) as ImageView
        closeButton.setOnClickListener {
            binding.searchView.setQuery(null, false)
            binding.searchView.clearFocus()
        }

        presenter.loadWords()
    }

    override fun showWords(cursor: Cursor) {
        requireActivity().runOnUiThread {
            adapter.setCursor(cursor, currentQuery)
        }
    }
}