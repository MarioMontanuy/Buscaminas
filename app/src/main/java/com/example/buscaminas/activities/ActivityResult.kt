package com.example.buscaminas.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.buscaminas.R
import com.example.buscaminas.log.DataSingleton
import com.example.buscaminas.databinding.ActivityResultBinding
import com.example.buscaminas.database.GameResult
import com.example.buscaminas.database.GameResultApplication
import com.example.buscaminas.database.GameResultViewModel
import com.example.buscaminas.database.GameResultViewModelFactory

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
        binding.buttonLeave.setOnClickListener { finishGame() }
        val item = findViewById<Button>(R.id.buttonCheckGames)
        item.setOnClickListener { consultGames() }
        addDataToDatabase()
    }

    private fun addStartingData() {
        binding.textDayData.text = DataSingleton.dateFormat.format(DataSingleton.currentTime)
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
        DataSingleton.setDefaultValues()
        finish()
    }

    private fun startConfig() {
        val intent = Intent(this, ActivityConfig::class.java)
        startActivity(intent)
    }

    private fun finishGame() {
        DataSingleton.setDefaultValues()
        finish()
    }

    private fun consultGames() {
        val intent = Intent(this, ConsultGamesActivity::class.java)
        startActivity(intent)
    }

    private fun addDataToDatabase() {
        val word = GameResult(
            DataSingleton.playerName,
            DataSingleton.dateFormat.format(DataSingleton.currentTime),
            DataSingleton.gridSize,
            DataSingleton.minePercentage,
            DataSingleton.mineNumber,
            DataSingleton.timeLeft,
            DataSingleton.timeControl,
            DataSingleton.squaresLeft,
            DataSingleton.mineSquare,
            DataSingleton.gameResult
        )
        gameResultViewModel.insert(word)
    }

    private val gameResultViewModel: GameResultViewModel by viewModels {
        GameResultViewModelFactory((application as GameResultApplication).repository)
    }
}