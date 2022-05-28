
package com.example.buscaminas.database.db.roomexample
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.buscaminas.R


class GameResultListAdapter(private val onClickListener:(GameResult) -> Unit, private val onLongClickItemSelected:(GameResult) -> Boolean) : ListAdapter<GameResult, GameResultListAdapter.GameResultViewHolder>(GameResultComparator()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameResultViewHolder {
        return GameResultViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: GameResultViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, onClickListener, onLongClickItemSelected)
    }

    class GameResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(gameResult: GameResult, onClickListener:(GameResult) -> Unit, onLongClickItemSelected:(GameResult) -> Boolean) {
            val text = "   ${gameResult.playerName} --- ${gameResult.gameDate}\n   Resultado de la partida: ${gameResult.gameResult}"
            wordItemView.text = text
            wordItemView.setOnClickListener{
                onClickListener(gameResult)
            }
            wordItemView.setOnLongClickListener(){
                onLongClickItemSelected(gameResult)
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

/*
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.buscaminas.R
import java.util.*


class WordListAdapter(context: Context, string: Array<String>) :
    RecyclerView.Adapter<WordListAdapter.ViewHolder>() {
    private val inflater: LayoutInflater
    private var vectorString: Array<String>

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titulo: TextView

        init {
            titulo = itemView.findViewById<View>(R.id.textView) as TextView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = inflater.inflate(R.layout.recyclerview_item, null)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val string = vectorString[position]
        holder.titulo.text = string
    }

    override fun getItemCount(): Int {
        return vectorString.size
    }

    init {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        vectorString = string
    }




}
*/

