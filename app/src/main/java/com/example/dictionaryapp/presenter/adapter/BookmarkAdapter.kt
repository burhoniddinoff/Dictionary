package com.example.dictionaryapp.presenter.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.data.model.WordData
import com.example.dictionaryapp.databinding.ItemFavouriteBinding

class BookmarkAdapter : RecyclerView.Adapter<BookmarkAdapter.MyWordViewHolder>() {

    lateinit var clickVolume: (String) -> Unit
    lateinit var clickWordItem: (String, String, String) -> Unit
    lateinit var updateWord: (WordData) -> Unit

    private var cursor: Cursor? = null
    private var query: String? = null

    private var lastAnimatedItemPosition = -1


    inner class MyWordViewHolder(private val binding: ItemFavouriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dictionary: WordData) {
            binding.english.text = dictionary.english
            binding.uzbek.text = dictionary.uzbek

            binding.root.setOnClickListener {
                clickWordItem.invoke(dictionary.english, dictionary.uzbek, dictionary.transcript)
            }


            binding.imgLike.setOnClickListener {
                updateWord.invoke(dictionary.copy(is_favourite = 0))
            }
        }

        init {
//            binding.icVolume.setOnClickListener {
//                clickVolume.invoke(binding.word1.text.toString())
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyWordViewHolder {
        return MyWordViewHolder(
            ItemFavouriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = if (cursor == null) 0 else cursor!!.count

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: MyWordViewHolder, @SuppressLint("RecyclerView") position: Int) {

//        if (lastAnimatedItemPosition < position) {
//            animateItem(holder.itemView)
//            lastAnimatedItemPosition = position
//        }

        cursor?.let {
            it.moveToPosition(position)

            val id = it.getLong(it.getColumnIndex("id"))
            val english = it.getString(it.getColumnIndex("english"))
            val type = it.getString(it.getColumnIndex("type"))
            val transcript = it.getString(it.getColumnIndex("transcript"))
            val uzbek = it.getString(it.getColumnIndex("uzbek"))
            val countable = it.getString(it.getColumnIndex("countable"))
            val is_favourite = it.getInt(it.getColumnIndex("is_favourite"))

            val dictionaryModel = WordData(id, english, type, transcript, uzbek, countable, is_favourite)
            holder.bind(dictionaryModel)
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setCursor(cursor: Cursor, query: String? = null) {
        this.cursor?.close()
        this.cursor = cursor
        this.query = query
        notifyDataSetChanged()
    }

    private fun animateItem(view: View) {
        view.translationY = (view.context as Activity).window.decorView.height.toFloat()
        view.animate()
            .translationY(0f)
            .setInterpolator(DecelerateInterpolator(2f))
            .setDuration(900)
            .start()
    }
}