package com.example.buscaminas.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buscaminas.R
import com.example.buscaminas.database.db.roomexample.*
import com.example.buscaminas.log.DataSingleton

class ListFragment : Fragment() {

    var recyclerView : RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val vista = inflater.inflate(R.layout.list_fragment, container, false)
        recyclerView = vista.findViewById(R.id.recyclerview)
        val adapter = GameResultListAdapter({gameResult -> onClickItemSelected(gameResult)}, {gameResult -> onLongClickItemSelected(gameResult)})
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(context)
        gameResultViewModel.allWords.observe(viewLifecycleOwner, Observer { gameResults ->
            gameResults?.let { adapter.submitList(it) }
        })
        val button = vista.findViewById<Button>(R.id.buttonBackToMainScreen)
        button?.setOnClickListener{
            activity?.finish()
        }
        return vista
    }

    private fun onClickItemSelected(gameResult: GameResult){
        DataSingleton.currentGame = gameResult
        val detailFragment = DetailFragment()
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragmentGamesResult, detailFragment)?.commit()
    }

    private fun onLongClickItemSelected(gameResult: GameResult) : Boolean{
//        Toast.makeText(this, "Click largo la palabra es: " + gameResult.playerName, Toast.LENGTH_SHORT).show()
        return true
    }

    private val gameResultViewModel: GameResultViewModel by viewModels {
        GameResultViewModelFactory((activity?.applicationContext as GameResultApplication).repository)
    }
}