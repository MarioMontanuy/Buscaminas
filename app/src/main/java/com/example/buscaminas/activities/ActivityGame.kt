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


class ActivityGame : AppCompatActivity(), AdapterView.OnItemClickListener,
    AdapterView.OnItemLongClickListener {
    private lateinit var binding: ActivityGameBinding
    private var resultData = ""
    private var viewModel = GridModel()
    private var milliSeconds: Long = 180000
    private var countDownInterval: Long = 1000
    private var gridAdapter = GridAdapter(this, ArrayList())
    private var gameFinished = false
    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configLayout()
        if (savedInstanceState != null) {
            milliSeconds = savedInstanceState.getLong("milliSeconds")
            viewModel = savedInstanceState.getParcelable("viewModel")!!
            gameFinished = savedInstanceState.getBoolean("gameFinished")
            resultData += savedInstanceState.getString("resultData")
            binding.textViewSquaresLeft.text = DataSingleton.squaresLeft.toString()
        } else {
            createLiveData()
            DataSingleton.squaresLeft = (DataSingleton.gridSize * DataSingleton.gridSize) - viewModel.countShowedSquares() - DataSingleton.mineNumber
            binding.textViewSquaresLeft.text = DataSingleton.squaresLeft.toString()
        }
        setAdapter()
        createObserver()
        checkControlTime()
        if (gameFinished) {
            binding.buttonShowResults.visibility = View.VISIBLE
            timer?.cancel()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("viewModel", viewModel)
        outState.putLong("milliSeconds", milliSeconds)
        outState.putBoolean("gameFinished", gameFinished)
        outState.putString("resultData", resultData)
    }

    private fun configLayout() {
        val preferences = getSharedPreferences("com.example.buscaminas_preferences", Context.MODE_PRIVATE)
        DataSingleton.playerName = preferences.getString("playerName", "Jugador").toString()
        DataSingleton.gridSize = preferences.getString("preferenceGridSize", "5")!!.toInt()
        DataSingleton.minePercentage = preferences.getString("preferenceBombPercentage", "15")!!.toDouble()
        DataSingleton.timeControl = preferences.getBoolean("time", false)
        DataSingleton.mineNumber = (DataSingleton.gridSize * DataSingleton.gridSize * (DataSingleton.minePercentage / 100)).roundToInt()
        binding.textviewPlayerName.text = DataSingleton.playerName
        binding.textViewNumBombs.text = DataSingleton.mineNumber.toString()
        binding.gridview.numColumns = DataSingleton.gridSize
        milliSeconds = (DataSingleton.gridSize * 40000).toLong()
        binding.buttonShowResults.setOnClickListener { showResults() }
    }

    private fun showResults() {
        gameFinished()
        finish()
    }

    private fun createLiveData() {
        viewModel = ViewModelProvider(this).get(GridModel::class.java)
        viewModel.gridModel()
    }

    private fun createObserver() {
        val observer: Observer<ArrayList<GridItem>> =
            Observer {
                gridAdapter.notifyDataSetChanged()
            }
        viewModel.getLiveDataGridItems().observe(this, observer)
    }

    private fun setAdapter() {
        gridAdapter = GridAdapter(this, viewModel.getLiveDataGridItems().value!!)
        binding.gridview.adapter = gridAdapter
        binding.gridview.onItemClickListener = this
        binding.gridview.onItemLongClickListener = this
    }

    @SuppressLint("SetTextI18n")
    private fun checkControlTime() {
        if (DataSingleton.timeControl) {
            binding.textViewCountDown.text = "${milliSeconds / countDownInterval} segundos"
            timer = object : CountDownTimer(milliSeconds, countDownInterval) {
                override fun onTick(millisUntilFinished: Long) {
                    binding.textViewCountDown.text =
                        "${millisUntilFinished / countDownInterval} segundos"
                    milliSeconds = millisUntilFinished
                }

                override fun onFinish() {
                    startSound("gameOverSound")
                    DataSingleton.gameResult = "Derrota"
                    showPopUp()
                }
            }.start()
        } else {
            binding.textViewCountDown.visibility = View.GONE
            binding.imageViewCountDown.visibility = View.GONE
        }
    }

    override fun onItemClick(grid: AdapterView<*>, view: View, position: Int, id: Long) {
        if (!gameFinished) {
            val result = viewModel.doAction(position, "onItemClick")
            if (result == "Bomb") {
                startSound("bombSound")
                showPopUp()
            }else if (result == "Win"){
                startSound("winnerSound")
                showPopUp()
            }
        }
        DataSingleton.squaresLeft = (DataSingleton.gridSize * DataSingleton.gridSize) - viewModel.countShowedSquares() - DataSingleton.mineNumber
        binding.textViewSquaresLeft.text = DataSingleton.squaresLeft.toString()
    }

    private fun showPopUp() {
        timer?.cancel()
        gameFinished = true
        viewModel.showBombs()
        binding.buttonShowResults.visibility = View.VISIBLE
        val intent = Intent(this, PopUpFinishGame::class.java)
       /* val bundle = Bundle()
        bundle.putString("data", resultData)
        intent.putExtras(bundle)*/
        startActivity(intent)
    }

    private fun gameFinished() {
        val intent = Intent(this, ActivityResult::class.java)
        /*val bundle = Bundle()
        resultData += "\nAlias: $playerName\nTamaño de la cuadrícula: $gridSize x $gridSize\nNúmero de minas: $numBombs\nCasillas por descubrir: ${(gridSize * gridSize) - viewModel.countShowedSquares() - numBombs}\n"
        if (binding.textViewCountDown.visibility != View.GONE) {
            resultData += "Tiempo restante: ${binding.textViewCountDown.text}\n"
        }

        Log.i("ActivityGame", resultData)
        bundle.putString("result", resultData)
        intent.putExtras(bundle)*/
        DataSingleton.squaresLeft = (DataSingleton.gridSize * DataSingleton.gridSize) - viewModel.countShowedSquares() - DataSingleton.mineNumber
        DataSingleton.timeLeft = binding.textViewCountDown.text.toString()
        DataSingleton.currentTime = Date().toString()
        startActivity(intent)
    }

    override fun onItemLongClick(
        grid: AdapterView<*>,
        view: View,
        position: Int,
        id: Long
    ): Boolean {
        if (!gameFinished) {
            viewModel.doAction(position, "onItemLongClick")
        }
        return true
    }

    private fun startSound(sound: String) {
        val intentService = Intent(this, SoundService::class.java)
        intentService.putExtra("sound", sound)
        this.startService(intentService)
    }
}