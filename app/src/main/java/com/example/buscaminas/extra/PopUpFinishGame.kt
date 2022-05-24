package com.example.buscaminas.extra

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import com.example.buscaminas.R
import com.example.buscaminas.log.DataSingleton
import com.example.buscaminas.databinding.PopupFinishgameBinding

class PopUpFinishGame : AppCompatActivity() {

    private lateinit var binding: PopupFinishgameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.popup_finishgame)
        binding = PopupFinishgameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val windowSize = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(windowSize)
        val width = windowSize.widthPixels
        val height = windowSize.heightPixels
        window.setLayout((width * 0.85).toInt(), (height * 0.25).toInt())
        binding.dataText.text = DataSingleton.gameResult
        binding.acceptButton.setOnClickListener { finish() }
    }
}