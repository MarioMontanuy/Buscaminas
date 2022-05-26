package com.example.buscaminas.fragments

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.buscaminas.R
import com.example.buscaminas.activities.ActivityConfig
import com.example.buscaminas.activities.DetailActivity
import com.example.buscaminas.database.*
import com.example.buscaminas.databinding.GamesPlayedBinding
import kotlinx.coroutines.launch

// TODO unused
class ListActivity : AppCompatActivity() {

    private lateinit var binding: GamesPlayedBinding
    //val app = applicationContext as ResultDataApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GamesPlayedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonBackToMainScreen.setOnClickListener { finish() }
        /*val fragmentListado = supportFragmentManager.findFragmentById(R.id.fragList) as ListFragment
        fragmentListado.setResultListener(this)*/
        /*val adapter = ResultDataAdapter()
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        ResultViewModel.allWords.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter.submitList(it) }
        })*/
        /*lifecycleScope.launch {
            val people = app.room
        }*/
    }

    /*private val ResultViewModel: ResultDataViewModel by viewModels {
        ResultDataViewModelFactory((applicationContext as ResultDataApplication).repository)
    }
*/
    /*override fun onResultSelected(resultData: String) {
        if (supportFragmentManager.findFragmentById(R.id.fragDetail) != null){
            val fragment = supportFragmentManager.findFragmentById(R.id.fragDetail) as DetailFragment
            if(fragment.isVisible){
                fragment.showText(resultData)
            }else{
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra("data", resultData)
                startActivity(intent)
            }
        }else{
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("data", resultData)
            startActivity(intent)
        }
    }*/
}



