package com.example.buscaminas.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.ContactsContract
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.buscaminas.game.GridAdapter
import com.example.buscaminas.game.GridItem
import com.example.buscaminas.game.GridModel
import com.example.buscaminas.log.DataSingleton
import com.example.buscaminas.databinding.ActivityGameBinding
import com.example.buscaminas.extra.PopUpFinishGame
import com.example.buscaminas.service.SoundService
import java.util.Date
import kotlin.collections.ArrayList
import kotlin.math.roundToInt


class ActivityGame : AppCompatActivity(){
    private lateinit var binding: ActivityGameBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


}