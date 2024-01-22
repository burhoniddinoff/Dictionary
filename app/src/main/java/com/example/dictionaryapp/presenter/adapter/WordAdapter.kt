package com.example.dictionaryapp.presenter.adapter

import android.annotation.SuppressLint
import android.database.Cursor
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.data.model.WordData
import com.example.dictionaryapp.databinding.ItemWordBinding
import com.example.dictionaryapp.utils.createSpannable
import java.util.Locale

class WordAdapter : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    private var cursor: Cursor? = null
    private var query: String? = null
    private lateinit var mTTS: TextToSpeech


    inner class WordViewHolder(private val binding: ItemWordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val save = binding.save

        fun bind(item: WordData) {
            if (query == null) {
                binding.english.text = item.english
                binding.uzbek.text = item.uzbek
            } else {
                binding.english.text =
                    item.english.createSpannable(query!!.toLowerCase(Locale.ROOT))
                binding.uzbek.text = item.uzbek
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {

        val holder = WordViewHolder(
            ItemWordBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

        mTTS = TextToSpeech(parent.context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = mTTS.setLanguage(Locale.GERMAN)

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(parent.context, "Language not supported", Toast.LENGTH_SHORT).show()
                } else {
                    holder.save.isEnabled = true
                }

            } else {
                Toast.makeText(parent.context, "Language not supported", Toast.LENGTH_SHORT).show()
            }
        }

        return holder
    }

    override fun getItemCount(): Int = this.cursor?.count ?: 0

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        this.cursor?.let {
            it.moveToPosition(position)
            val english = it.getString(it.getColumnIndex("english"))
            val uzbek = it.getString(it.getColumnIndex("uzbek"))
            val transcript = it.getString(it.getColumnIndex("transcript"))
            val isFavourite = it.getInt(it.getColumnIndex("is_favourite"))
            val countable = it.getString(it.getColumnIndex("countable"))
            val type = it.getString(it.getColumnIndex("type"))

            val item = WordData(
                0, english.toString(), type, transcript, uzbek.toString(), countable, isFavourite
            )
            holder.bind(item)
        }
    }

}