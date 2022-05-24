package com.example.buscaminas.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.buscaminas.databinding.ActivityHelpBinding

class ActivityHelp : AppCompatActivity(){
    private lateinit var binding: ActivityHelpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonBackToGame.setOnClickListener{ backToGame() }
    }

    private fun backToGame(){
        finish()
    }
}