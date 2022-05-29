package com.example.buscaminas.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.buscaminas.R
import com.example.buscaminas.log.DataSingleton

class DetailFragment : Fragment() {
    private var textView: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addData()
    }

    private fun addData() {
        textView = requireView().findViewById(R.id.textViewDetailFragment)
        textView?.text = DataSingleton.currentGame?.getDetailedData()
    }
}