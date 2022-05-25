package com.example.buscaminas.database

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.buscaminas.R

class ResultDataAdapter : ListAdapter<ResultDataEntity, ResultDataAdapter.WordViewHolder>(WordsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.playerName)
    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String?) {
            wordItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): WordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return WordViewHolder(view)
            }
        }
    }

    class WordsComparator : DiffUtil.ItemCallback<ResultDataEntity>() {
        override fun areItemsTheSame(oldItem: ResultDataEntity, newItem: ResultDataEntity): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ResultDataEntity, newItem: ResultDataEntity): Boolean {
            return oldItem.playerName == newItem.playerName
        }
    }
}
