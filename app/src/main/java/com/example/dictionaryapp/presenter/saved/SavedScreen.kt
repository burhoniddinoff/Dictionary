package com.example.dictionaryapp.presenter.saved

import android.app.Dialog
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionaryapp.R
import com.example.dictionaryapp.data.local.MySharedPref
import com.example.dictionaryapp.data.model.WordData
import com.example.dictionaryapp.databinding.ScreenSavedWordsBinding
import com.example.dictionaryapp.presenter.adapter.TempAdapter
import com.example.dictionaryapp.presenter.adapter.SavedWordsAdapter
import com.example.dictionaryapp.presenter.adapter.SavedWordsUzAdapter
import com.example.dictionaryapp.utils.popBackStack

class SavedScreen : Fragment(R.layout.screen_saved_words), SavedContract.View {

    private var _binding: ScreenSavedWordsBinding? = null
    private val binding get() = _binding!!
    private val list = mutableListOf<WordData>()
    private lateinit var adapter: TempAdapter
    private lateinit var presenter: SavedContract.Presenter
    private lateinit var dialog: Dialog
    var tts: TextToSpeech? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = ScreenSavedWordsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = SavedPresenter(this)
        dialog = Dialog(requireContext())
        val shared = MySharedPref
        val pos = shared.getOpenScreen()

        presenter.loadCursor()

        binding.icBackBtn.setOnClickListener {
            popBackStack()
        }

        when (pos) {
            0 -> {
                presenter.loadCursor()
                adapter = SavedWordsAdapter(list)
            }

            1 -> {
                presenter.loadUzList()
                adapter = SavedWordsUzAdapter(list)
            }
        }

        binding.rvBookmark.adapter = when (pos) {
            0 -> {
                adapter as SavedWordsAdapter
            }

            else -> {
                adapter as SavedWordsUzAdapter
            }
        }

        binding.rvBookmark.layoutManager = LinearLayoutManager(requireContext())


    }

    override fun cursor(cursor: List<WordData>) {
        list.clear()
        list.addAll(cursor)
        if (cursor.isEmpty()) {
            binding.placeHolder.visibility = View.VISIBLE
        } else {
            binding.placeHolder.visibility = View.GONE
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}