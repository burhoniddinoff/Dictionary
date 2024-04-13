package com.example.dictionaryapp.presenter.adapter

import android.annotation.SuppressLint
import android.app.VoiceInteractor
import android.database.Cursor
import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.R
import com.example.dictionaryapp.data.local.MySharedPref
import com.example.dictionaryapp.data.model.WordData
import com.example.dictionaryapp.databinding.ItemWordBinding
import com.example.dictionaryapp.utils.createSpannable
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.Locale
import kotlin.system.exitProcess

class WordAdapter(private var context: Context) :
    RecyclerView.Adapter<WordAdapter.WordViewHolder>(), TextToSpeech.OnInitListener {

    private var cursor: Cursor? = null
    private var query: String? = null
    var isEnglish = false
    private var tts: TextToSpeech = TextToSpeech(context, this)
    lateinit var isFavourite: (WordData) -> Unit

    inner class WordViewHolder(private val binding: ItemWordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(wordData: WordData) {
//            if (wordData.is_favourite == 1) binding.imgLike.setImageResource(R.drawable.ic_favourite_2)
//            else binding.imgLike.setImageResource(R.drawable.ic_favourite)


            if (query == null) {
                binding.uzbek.text = wordData.english
                binding.english.text = wordData.uzbek
            } else {
                binding.english.text =
                    wordData.uzbek.createSpannable(query!!.toLowerCase(Locale.ROOT))
                binding.uzbek.text = wordData.english
            }

            binding.imgSpeak.setOnClickListener {
                speakOut(wordData.english)
            }

//            binding.imgSpeak.setOnClickListener {
//                speakOut(wordData.uzbek)
//            }

            binding.imgLike.setOnClickListener {
                Log.d("TTT", "Adapter: IMGLIKE bosildi")


                isFavourite.invoke(
                    WordData(
                        id = wordData.id,
                        english = wordData.uzbek,
                        type = wordData.type,
                        transcript = wordData.transcript,
                        uzbek = wordData.english,
                        countable = wordData.countable,
                        is_favourite = if (wordData.is_favourite == 0) 1 else 0,
                    )
                )

                binding.imgLike.setImageResource(if (wordData.is_favourite == 0) R.drawable.ic_favourite_2 else R.drawable.ic_favourite)
            }

            binding.imgLike.setImageResource(if (wordData.is_favourite == 0) R.drawable.ic_favourite else R.drawable.ic_favourite_2)
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
                val id = it.getLong(it.getColumnIndex("id"))
                val uzbek = it.getString(it.getColumnIndex("uzbek"))
                val transcript = it.getString(it.getColumnIndex("transcript"))
                val is_favourite = it.getInt(it.getColumnIndex("is_favourite"))
                val countable = it.getString(it.getColumnIndex("countable"))
                val type = it.getString(it.getColumnIndex("type"))

                val item = WordData(
                    id,
                    english.toString(),
                    type,
                    transcript,
                    uzbek.toString(),
                    countable,
                    is_favourite,
                )
                holder.bind(item)
            }
        } else {
            this.cursor?.let {
                it.moveToPosition(position)

                val english = it.getString(it.getColumnIndex("english"))
                val id = it.getLong(it.getColumnIndex("id"))
                val uzbek = it.getString(it.getColumnIndex("uzbek"))
                val transcript = it.getString(it.getColumnIndex("transcript"))
                val is_favourite = it.getInt(it.getColumnIndex("is_favourite"))
                val countable = it.getString(it.getColumnIndex("countable"))
                val type = it.getString(it.getColumnIndex("type"))

                val item = WordData(
                    id,
                    uzbek.toString(),
                    type,
                    transcript,
                    english.toString(),
                    countable,
                    is_favourite,
                )
                holder.bind(item)
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

    // awfs

//    @SuppressLint("NotifyDataSetChanged")
//    fun setLanguages(bool: Boolean) {
//        isEnglish = bool
//        notifyDataSetChanged()
//    }

}