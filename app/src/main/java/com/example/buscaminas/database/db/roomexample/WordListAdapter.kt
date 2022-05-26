
package com.example.buscaminas.database.db.roomexample
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.buscaminas.R


class WordListAdapter(private val onClickListener:(Word) -> Unit, private val onLongClickItemSelected:(Word) -> Boolean) : ListAdapter<Word, WordListAdapter.WordViewHolder>(WordsComparator()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, onClickListener, onLongClickItemSelected)
    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(word: Word, onClickListener:(Word) -> Unit, onLongClickItemSelected:(Word) -> Boolean) {
            wordItemView.text = word.playerName
            wordItemView.setOnClickListener{
                onClickListener(word)
            }
            wordItemView.setOnLongClickListener(){
                onLongClickItemSelected(word)
            }
        }

        companion object {
            fun create(parent: ViewGroup): WordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return WordViewHolder(view)
            }
        }

    }

    class WordsComparator : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.playerName == newItem.playerName
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

