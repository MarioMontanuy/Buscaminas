package com.example.buscaminas.activities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.buscaminas.R

class ConsultGamesActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.games_played)
    }



    /*private fun onClickItemSelected(gameResult: GameResult){
//        Toast.makeText(this, "Click corto la palabra es: " + gameResult.playerName, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, DetailActivity::class.java)
        DataSingleton.currentGame = gameResult
        startActivity(intent)
    }

    private fun onLongClickItemSelected(gameResult: GameResult) : Boolean{
//        Toast.makeText(this, "Click largo la palabra es: " + gameResult.playerName, Toast.LENGTH_SHORT).show()
        return true
    }

    private val gameResultViewModel: GameResultViewModel by viewModels {
        GameResultViewModelFactory((application as GameResultApplication).repository)
    }*/

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