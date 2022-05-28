package com.example.buscaminas.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import com.example.buscaminas.R
import com.example.buscaminas.log.DataSingleton

class DetailFragment: Fragment() {
    private var textView: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addData()
        buttonController()
    }

    private fun addData(){
        textView = requireView().findViewById(R.id.textViewDetailFragment)
        textView?.text = DataSingleton.currentGame?.getDetailedData()
    }

    private fun buttonController(){
        val button = requireView().findViewById<Button>(R.id.buttonBackToList)
        button.setOnClickListener {
            val listFragment = ListFragment()
            val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentGamesResult, listFragment)?.commit()
        }
    }
}