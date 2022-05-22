package com.example.buscaminas

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.buscaminas.databinding.GamesPlayedBinding


class GamesPlayed : AppCompatActivity(), ListFragment.ResultListener {

    private lateinit var binding: GamesPlayedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GamesPlayedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonBackToMainScreen.setOnClickListener { finish() }
        val fragmentListado = supportFragmentManager.findFragmentById(R.id.fragList) as ListFragment
        fragmentListado.setResultListener(this)
    }

    override fun onResultSelected(resultData: String) {
        if (supportFragmentManager.findFragmentById(R.id.fragDetail) != null){
            println("NO ES NULO")
            val fragment = supportFragmentManager.findFragmentById(R.id.fragDetail) as DetailFragment
            if(fragment.isVisible){
                fragment.showText(resultData)
            }else{
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra("data", resultData)
                startActivity(intent)
            }
        }else{
            println("ES NULO")
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("data", resultData)
            startActivity(intent)
        }
    }
}



