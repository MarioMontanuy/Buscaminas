package com.example.buscaminas

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.buscaminas.databinding.ActivityResultBinding
import java.util.*

class ActivityResult: AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private var log = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addStartingData()
        binding.buttonEmail.setOnClickListener{ sendEmail() }
        binding.buttonNewGame.setOnClickListener{ createNewGame() }
        binding.buttonLeave.setOnClickListener{ finish() }
    }

    private fun addStartingData(){
        binding.textDayData.text = Date().toString()
        log += intent.getStringExtra("logData")
        log += intent.getStringExtra("result")
        binding.textLogData.text = log
    }

    private fun sendEmail(){
        val intent = Intent(Intent.ACTION_SENDTO)
        //intent.data = Uri.parse("mailto:"+getString(R.string.email)+"?subject=Greeting&body=Hello world!")
        //intent.data = Uri.parse("mailto:"+binding.editTextEmailData.text+"?subject=Buscaminas: Resultados partida&body=Estadísticas:"+binding.textLogData.text+" Fecha de la partida:"+binding.textDayData.text)
        var contenido = ""
        contenido += binding.textLogData.text
        contenido += "\n"
        contenido += "Fecha de la partida: "+ binding.textDayData.text

        intent.data = Uri.parse("mailto:")
        // TODO arreglar destinatario email
        intent.putExtra(Intent.EXTRA_EMAIL, binding.editTextEmailData.text)
        intent.putExtra(Intent.EXTRA_SUBJECT,"Buscaminas: Resultados partida")
        intent.putExtra(Intent.EXTRA_TEXT, contenido)
        startActivity(intent)
        // TODO ocultar boton??
        //binding.buttonEmail.visibility = View.GONE
    }
    private fun createNewGame(){
        val intent = Intent(this, ActivityConfig::class.java)
        startActivity(intent)
        finish()
    }
}