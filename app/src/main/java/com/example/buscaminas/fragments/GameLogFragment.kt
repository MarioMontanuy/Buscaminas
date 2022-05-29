package com.example.buscaminas.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.buscaminas.R
import com.example.buscaminas.log.DataSingleton

class GameLogFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val newView = inflater.inflate(R.layout.fragment_game_log, container, false)
        newView.findViewById<TextView>(R.id.textViewLogFragment).text = DataSingleton.getLogData()
        if (savedInstanceState != null) {
            newView.findViewById<TextView>(R.id.textViewLogFragmentCurrentClick)?.text =
                savedInstanceState.getString("log")
        }
        return newView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(
            "log",
            activity?.findViewById<TextView>(R.id.textViewLogFragmentCurrentClick)?.text.toString()
        )

    }

}