package com.example.dictionaryapp.presenter.adapter

import android.annotation.SuppressLint
import android.database.Cursor
import android.content.Context
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.databinding.ItemWordBinding
import com.example.dictionaryapp.utils.createSpannable
import java.util.Locale

class WordAdapter(private var context: Context) :
    RecyclerView.Adapter<WordAdapter.WordViewHolder>(), TextToSpeech.OnInitListener {

    private var cursor: Cursor? = null
    private var query: String? = null
    var isEnglish = false
    private var tts: TextToSpeech = TextToSpeech(context, this)

    inner class WordViewHolder(private val binding: ItemWordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(english: String, uzbek: String) {
            if (query == null) {
                binding.uzbek.text = english
                binding.english.text = uzbek
            } else {
                binding.english.text = uzbek.createSpannable(query!!.toLowerCase(Locale.ROOT))
                binding.uzbek.text = english
            }

            binding.imgSpeak.setOnClickListener {
                speakOut(english)
            }

            binding.imgSpeak.setOnClickListener {
                speakOut(uzbek)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCursor(cursor: Cursor, query: String? = null) {
        this.cursor?.close()
        this.cursor = cursor
        this.query = query
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder =
        WordViewHolder(
            ItemWordBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount(): Int = this.cursor?.count ?: 0

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        if (isEnglish) {
            this.cursor?.let {
                it.moveToPosition(position)
                val english = it.getString(it.getColumnIndex("english"))
                val uzbek = it.getString(it.getColumnIndex("uzbek"))
                holder.bind(english.toString(), uzbek.toString())
            }
        } else {
            this.cursor?.let {
                it.moveToPosition(position)
                val english = it.getString(it.getColumnIndex("english"))
                val uzbek = it.getString(it.getColumnIndex("uzbek"))
                holder.bind(uzbek.toString(), english.toString())
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show()
        }
    }

    private fun speakOut(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

}