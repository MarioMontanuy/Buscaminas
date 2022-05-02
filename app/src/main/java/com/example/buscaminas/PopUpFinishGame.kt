package com.example.buscaminas

import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
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
        window.setLayout((width * 0.85).toInt(), (height * 0.5).toInt())
        setResult(RESULT_OK, intent)
        binding.dataText.text = intent.getStringExtra("data")
        println("DATA ${binding.dataText.text}")
        binding.acceptButton.setOnClickListener { finish() }
    }
}