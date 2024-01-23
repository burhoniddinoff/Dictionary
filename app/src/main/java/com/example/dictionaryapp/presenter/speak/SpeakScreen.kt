package com.example.dictionaryapp.presenter.speak

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionaryapp.R
import com.example.dictionaryapp.databinding.ScreenSpeakBinding

class SpeakScreen : Fragment(R.layout.screen_speak), SpeakContract.View {

    private val binding: ScreenSpeakBinding by viewBinding(ScreenSpeakBinding::bind)
    private val presenter: SpeakContract.Presenter by lazy { SpeakPresenter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.english.setOnClickListener {
            presenter.onEnglishButtonClicked()
        }

        binding.uzbek.setOnClickListener {
            presenter.onUzbekButtonClicked()
        }
    }

    override fun updateResults(s: String, translation: String) {
        Toast.makeText(requireContext(), s, Toast.LENGTH_LONG).show()
        binding.usage.text = s
        binding.translate.text = translation
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.onActivityResult(requestCode, resultCode, data)
    }

    override fun promptSpeechInput(isUzbek: Boolean, reqCode: Int) {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        if (isUzbek) {
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "uz-UZ")
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Gapiring")
        } else {
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-GB")
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something")
        }
        try {
            startActivityForResult(intent, reqCode)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                requireContext(),
                "Sorry! Your device doesn't support speech input",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


}