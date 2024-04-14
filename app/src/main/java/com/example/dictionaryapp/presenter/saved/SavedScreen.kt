package com.example.dictionaryapp.presenter.saved

import android.database.Cursor
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionaryapp.R
import com.example.dictionaryapp.databinding.ScreenSavedWordsBinding
import com.example.dictionaryapp.domain.AppRepositoryImpl
import com.example.dictionaryapp.presenter.adapter.BookmarkAdapter
import com.example.dictionaryapp.utils.popBackStack
import java.util.Locale
import java.util.concurrent.Executors

class SavedScreen : Fragment(R.layout.screen_saved_words) {

    private var _binding: ScreenSavedWordsBinding? = null
    private val binding get() = _binding!!
    private val executors = Executors.newSingleThreadExecutor()

    private lateinit var mTTS: TextToSpeech

    private lateinit var adapter: BookmarkAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = ScreenSavedWordsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = BookmarkAdapter()
        binding.rvBookmark.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvBookmark.adapter = adapter

        loadBookmarks()

        adapter.updateWord = {
            executors.execute {
                AppRepositoryImpl.getInstance().updateData(it)
                loadBookmarks()
            }
        }

        binding.icBackBtn.setOnClickListener { popBackStack() }



//        val dialog = Dialog(requireContext())
//        dialog.setContentView(R.layout.dialog_word_info)

//        adapter.clickWordItem = { english, uzbek, transicript ->
//            dialog.show()
//            dialog.window?.setBackgroundDrawable(ColorDrawable( Color.TRANSPARENT))
//            dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//            dialog.window?.setGravity(Gravity.CENTER)
//
//            dialog.findViewById<TextView>(R.id.main_word).text = capitalizeFirstLetter(english)
//            dialog.findViewById<TextView>(R.id.sub_word).text = uzbek
//            dialog.findViewById<TextView>(R.id.tv_transcription).text = "noun:[${transicript}]"
//
//            dialog.findViewById<TextView>(R.id.btn_close).setOnClickListener { dialog.dismiss() }
//        }

        mTTS = TextToSpeech(requireContext()) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = mTTS.setLanguage(Locale.ENGLISH)
                if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED
                ) {
                    Log.e("TTS", "Language not supported")
                } else {
                }
            } else {
                Log.e("TTS", "Initialization failed")
            }
        }

        adapter.clickVolume = {
            speak(it)
        }


    }

    private fun showBookmarks(cursor: Cursor) {
        requireActivity().runOnUiThread {
            binding.placeHolder.isVisible = cursor.count < 1

            adapter.setCursor(cursor)
        }
    }

    private fun loadBookmarks() {
        executors.execute {
            val cursor = AppRepositoryImpl.getInstance().getAllBookmarks(1)
            showBookmarks(cursor)
        }
    }

    private fun speak(text: String) {
        mTTS.setSpeechRate(0.45f)
        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


}