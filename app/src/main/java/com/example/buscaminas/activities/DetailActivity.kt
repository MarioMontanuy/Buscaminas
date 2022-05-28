package com.example.buscaminas.activities

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.buscaminas.R

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val button = findViewById<Button>(R.id.buttonBackToList)
        button.setOnClickListener {
            finish()
        }
    }
}