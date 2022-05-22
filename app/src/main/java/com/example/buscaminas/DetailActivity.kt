package com.example.buscaminas

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_fragment)
        val button = findViewById<Button>(R.id.buttonBackToList)
        val textView = findViewById<TextView>(R.id.textViewDetailFragment)
        textView.text = intent.getStringExtra("data")
        button.setOnClickListener { finish() }
    }
}