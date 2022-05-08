package com.example.buscaminas

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.buscaminas.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonHelp.setOnClickListener { showHelp() }
        binding.buttonStartGame.setOnClickListener { startGame() }
        binding.buttonLeave.setOnClickListener { finish() }
    }

    private fun showHelp() {
        val intent = Intent(this, ActivityHelp::class.java)
        startActivity(intent)
    }

    private fun startGame() {
        val intent = Intent(this, ActivityConfig::class.java)
        startActivity(intent)
        finish()
    }
}