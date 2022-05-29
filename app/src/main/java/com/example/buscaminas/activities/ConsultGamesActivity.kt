package com.example.buscaminas.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.buscaminas.R

class ConsultGamesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.games_played)
        val button = findViewById<Button>(R.id.buttonBackToMainScreen)
        button.setOnClickListener {
            finish()
        }
    }
}