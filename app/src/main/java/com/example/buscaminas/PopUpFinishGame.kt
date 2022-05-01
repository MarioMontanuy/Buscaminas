package com.example.buscaminas

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity

class PopUpFinishGame : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.popup_finishgame)
        val windowSize = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(windowSize)
        val width = windowSize.widthPixels
        val height = windowSize.heightPixels
        window.setLayout((width * 0.85).toInt(), (height * 0.5).toInt())
    }
}