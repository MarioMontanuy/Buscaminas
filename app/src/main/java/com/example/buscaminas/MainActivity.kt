package com.example.buscaminas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.buscaminas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonHelp.setOnClickListener{ showHelp() }
        binding.buttonStartGame.setOnClickListener{ startGame() }
        binding.buttonLeave.setOnClickListener{ finish() }


    }

    private fun showHelp(){
        Toast.makeText(this, "Help", Toast.LENGTH_LONG).show()
        val intent = Intent(this, ActivityHelp::class.java)
        startActivity(intent)
    }

    private fun startGame(){
        Toast.makeText(this, "StartGame", Toast.LENGTH_LONG).show()
        val intent = Intent(this, ActivityConfig::class.java)
        startActivity(intent)
        finish()
    }
}