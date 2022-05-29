package com.example.buscaminas.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.buscaminas.R
import com.example.buscaminas.database.GameResult
import com.example.buscaminas.fragments.DetailFragment
import com.example.buscaminas.fragments.ListFragment
import com.example.buscaminas.log.DataSingleton

class ConsultGamesActivity : AppCompatActivity(), ListFragment.ConsultGamesListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.games_played)
        val button = findViewById<Button>(R.id.buttonBackToMainScreen)
        button.setOnClickListener {
            finish()
        }
        val listFragment = supportFragmentManager.findFragmentById(R.id.fragmentGamesResult) as ListFragment
        listFragment.setConsultGamesListener(this)
    }

    override fun onClickItemSelectedConsultGames(gameResult: GameResult) {
        DataSingleton.currentGame = gameResult
        val frag =
            supportFragmentManager.findFragmentById(R.id.fragmentGamesResultDetail)
        if (frag != null) {
            val detailFragment = DetailFragment()
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentGamesResultDetail, detailFragment).commit()
        } else {
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }
    }
}