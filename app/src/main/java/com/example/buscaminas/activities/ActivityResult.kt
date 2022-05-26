package com.example.buscaminas.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.buscaminas.database.db.roomexample.WordViewModel
import com.example.buscaminas.database.db.roomexample.WordViewModelFactory
import com.example.buscaminas.database.db.roomexample.WordsApplication
import com.example.buscaminas.log.DataSingleton
import com.example.buscaminas.databinding.ActivityResultBinding

class ActivityResult : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

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
        binding.textDayData.text = DataSingleton.currentTime
        binding.textLogData.text = DataSingleton.getResult()
    }

    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SENDTO)
        var contenido = ""
        contenido += DataSingleton.getResult()
        contenido += "\n"
        contenido += "Fecha de la partida: " + DataSingleton.currentTime
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