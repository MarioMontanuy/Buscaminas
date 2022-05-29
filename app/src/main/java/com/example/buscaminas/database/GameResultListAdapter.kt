package com.example.buscaminas.database

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.buscaminas.R


class GameResultListAdapter(
    private val onClickListener: (GameResult) -> Unit,
    private val onLongClickItemSelected: (View, GameResult) -> Boolean
) : ListAdapter<GameResult, GameResultListAdapter.GameResultViewHolder>(
    GameResultComparator()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameResultViewHolder {
        return GameResultViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: GameResultViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, onClickListener, onLongClickItemSelected)
    }

    class GameResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(
            gameResult: GameResult,
            onClickListener: (GameResult) -> Unit,
            onLongClickItemSelected: (View, GameResult) -> Boolean
        ) {
            val text =
                "   ${gameResult.playerName} --- ${gameResult.gameDate}\n   Resultado de la partida: ${gameResult.gameResult}"
            wordItemView.text = text
            wordItemView.setOnClickListener {
                onClickListener(gameResult)
            }
            wordItemView.setOnLongClickListener() {
                onLongClickItemSelected(wordItemView, gameResult)
            }
        }

        companion object {
            fun create(parent: ViewGroup): GameResultViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return GameResultViewHolder(view)
            }
        }

    }

    class GameResultComparator : DiffUtil.ItemCallback<GameResult>() {
        override fun areItemsTheSame(oldItem: GameResult, newItem: GameResult): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: GameResult, newItem: GameResult): Boolean {
            return oldItem.gameDate == newItem.gameDate
        }
    }

}

