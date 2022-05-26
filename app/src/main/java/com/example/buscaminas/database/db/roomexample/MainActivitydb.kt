package com.example.buscaminas.database.db.roomexample
// Room codelab
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buscaminas.R
import com.example.buscaminas.activities.DetailActivity
import com.example.buscaminas.log.DataSingleton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivitydb : AppCompatActivity(){

    // private val newWordActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.games_played)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        /*val array = arrayOf("String1", "string2", "Prueba2")*/

        val adapter = WordListAdapter({word -> onClickItemSelected(word)}, {word -> onLongClickItemSelected(word)})
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        wordViewModel.allWords.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter.submitList(it) }
        })
        val button = findViewById<Button>(R.id.buttonBackToMainScreen)
        button.setOnClickListener{
                val word = Word("awda")
                wordViewModel.insert(word)
        }

    }

    private fun onClickItemSelected(word: Word){
        Toast.makeText(this, "Click corto la palabra es: " + word.playerName, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, DetailActivity::class.java)
        DataSingleton.currentGame = word
        startActivity(intent)
    }

    private fun onLongClickItemSelected(word: Word) : Boolean{
        Toast.makeText(this, "Click largo la palabra es: " + word.playerName, Toast.LENGTH_SHORT).show()
        return true
    }

    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((application as WordsApplication).repository)
    }



    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let {
                val word = Word(it)
                wordViewModel.insert(word)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show()
        }
    }*/


}