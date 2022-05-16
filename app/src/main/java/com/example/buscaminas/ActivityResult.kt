package com.example.buscaminas

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.buscaminas.databinding.ActivityResultBinding
import java.util.*

class ActivityResult : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private var log = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addStartingData()
        binding.buttonEmail.setOnClickListener { sendEmail() }
        binding.buttonNewGame.setOnClickListener { createNewGame() }
        binding.buttonConfiguration.setOnClickListener { startConfig() }
        binding.buttonLeave.setOnClickListener { finish() }
    }

    private fun addStartingData() {
        binding.textDayData.text = Date().toString()
        log += intent.getStringExtra("result")
        binding.textLogData.text = log
    }

    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SENDTO)
        var contenido = ""
        contenido += binding.textLogData.text
        contenido += "\n"
        contenido += "Fecha de la partida: " + binding.textDayData.text
        intent.data =
            Uri.parse("mailto:${binding.editTextEmailData.text}?subject=Buscaminas: Resultados partida&body=$contenido")
        startActivity(intent)
    }

    private fun createNewGame() {
        val intent = Intent(this, ActivityGame::class.java)
        startActivity(intent)
        finish()
    }

    private fun startConfig() {
        val intent = Intent(this, ActivityConfig::class.java)
        startActivity(intent)
    }
}