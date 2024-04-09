package com.example.dictionaryapp.presenter.home

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionaryapp.R
import com.example.dictionaryapp.databinding.ScreenHomeBinding
import com.example.dictionaryapp.presenter.adapter.WordAdapter
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request

class HomeScreen : Fragment(R.layout.screen_home), HomeContract.View {
    private val binding by viewBinding(ScreenHomeBinding::bind)
    private lateinit var adapter: WordAdapter
    private val presenter: HomeContract.Presenter by lazy { HomePresenter(this) }
    private var currentQuery: String? = null

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        adapter = WordAdapter(requireContext())
        binding.recycler.adapter = adapter

        adapter.isFavourite = {
            presenter.updateData(data = it)
            Log.d("TTT", "HomeScreen: IMGLIKE bosildi")
        }

        binding.imgTransfer.setOnClickListener {
            if (adapter.isEnglish) {
                adapter.isEnglish = false
                binding.searchView.queryHint = "Search"
            } else {
                adapter.isEnglish = true
                binding.searchView.queryHint = "Qidiring"
            }
            adapter.notifyDataSetChanged()
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                currentQuery = newText
                if (currentQuery == null) presenter.loadWords()
                else if (adapter.isEnglish) {
                    presenter.loadWordsByUz(currentQuery!!)
                    Log.d("TTT", "HOME SCREEN ${adapter.isEnglish}")
                } else {
                    presenter.loadWordsByEn(currentQuery!!)
                    Log.d("TTT", "HOME SCREEN ${adapter.isEnglish}")
                }

                return true
            }
        })

        val closeButton =
            binding.searchView.findViewById(androidx.appcompat.R.id.search_close_btn) as ImageView
        closeButton.setOnClickListener {
            binding.searchView.setQuery(null, false)
            binding.searchView.clearFocus()
        }

        presenter.loadWords()
    }

    fun searchText(text: String) {
        binding.searchView.setQuery(text, true)
    }

    override fun showWords(cursor: Cursor) {
        requireActivity().runOnUiThread {
            adapter.setCursor(cursor, currentQuery)
        }
    }
}