package com.example.buscaminas.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buscaminas.R
import com.example.buscaminas.activities.DetailActivity
import com.example.buscaminas.database.*
import com.example.buscaminas.log.DataSingleton

class ListFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    var button: Button? = null
    private var listener: ConsultGamesListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val vista = inflater.inflate(R.layout.fragment_list, container, false)
        button = vista.findViewById(R.id.buttonBackToMainScreen)
        recyclerView = vista.findViewById(R.id.recyclerview)
        val adapter = GameResultListAdapter({ gameResult -> onClickItemSelected(gameResult) },
            { itemView, gameResult -> onLongClickItemSelected(itemView, gameResult) })
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(context)
        gameResultViewModel.allWords.observe(viewLifecycleOwner, Observer { gameResults ->
            gameResults?.let { adapter.submitList(it) }
        })
        return vista
    }

    private fun onClickItemSelected(gameResult: GameResult) {
        if(listener != null){
            listener!!.onClickItemSelectedConsultGames(gameResult)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = try {
            context as ConsultGamesListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement OnCorreosListener")
        }
    }

    private fun onLongClickItemSelected(itemView: View, gameResult: GameResult): Boolean {
        val popupMenu = PopupMenu(context, itemView)
        popupMenu.inflate(R.menu.menu_popup)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {
                    gameResultViewModel.deleteEntry(gameResult.getId())
                    true
                }
                else -> {
                    true
                }
            }
        }
        popupMenu.show()
        return true
    }


    interface ConsultGamesListener {
        fun onClickItemSelectedConsultGames(gameResult: GameResult)
    }

    fun setConsultGamesListener(listener: ConsultGamesListener?) {
        this.listener = listener
    }

    private val gameResultViewModel: GameResultViewModel by viewModels {
        GameResultViewModelFactory((activity?.applicationContext as GameResultApplication).repository)
    }
}