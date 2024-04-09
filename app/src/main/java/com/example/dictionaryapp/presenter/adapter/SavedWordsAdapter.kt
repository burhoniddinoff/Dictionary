package com.example.dictionaryapp.presenter.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.dictionaryapp.R
import com.example.dictionaryapp.data.model.WordData

class SavedWordsAdapter(var list: List<WordData>) : Adapter<SavedWordsAdapter.WordViewHolder>(), TempAdapter {
    private var itemTouchListener: ((WordData) -> Unit)? = null

    inner class WordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val english = view.findViewById<TextView>(R.id.english)
        private val containerView = view.findViewById<ConstraintLayout>(R.id.containerItem)
        private val uzbek = view.findViewById<TextView>(R.id.uzbek)
        private val save = view.findViewById<ImageView>(R.id.imgLike)
        init {
            containerView.setOnClickListener {
                itemTouchListener?.invoke(list[adapterPosition])
            }
        }


        @SuppressLint("NotifyDataSetChanged")
        fun bind() {
            val item = list[adapterPosition]
            if (item.is_favourite == 1) save.setImageResource(R.drawable.ic_favourite_2)
            else save.setImageResource(R.drawable.ic_favourite)
            english.text = item.english
            uzbek.text = item.uzbek
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_favourite, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind()
    }

    fun setItemTouchListener(block: (WordData) -> Unit) {
        this.itemTouchListener = block
    }
}