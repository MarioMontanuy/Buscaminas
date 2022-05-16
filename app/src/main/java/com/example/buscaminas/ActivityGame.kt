package com.example.buscaminas

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.buscaminas.databinding.ActivityGameBinding
import kotlin.math.roundToInt


class ActivityGame : AppCompatActivity(), AdapterView.OnItemClickListener,
    AdapterView.OnItemLongClickListener {
    private lateinit var binding: ActivityGameBinding
    private var playerName = ""
    private var gridSize = 5
    private var numBombs = 0
    private var time = ""
    private var resultData = ""
    private var viewModel = GridModel()
    private var milliSeconds: Long = 180000
    private var countDownInterval: Long = 1000
    private var gridAdapter = GridAdapter(this, ArrayList())
    private var gameFinished = false
    var timer: CountDownTimer? = null

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
        } else {
            createLiveData()
        }
        setAdapter()
        createObserver()
        checkControlTime(time)
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
        playerName = preferences.getString("playerName", "Jugador").toString()
        println("palyerName: $playerName")
        gridSize = preferences.getString("preferenceGridSize", "5")!!.toInt()
        val bombPercentage = preferences.getString("preferenceBombPercentage", "15")!!.toDouble()
        time = preferences.getBoolean("time", false).toString()
        println("time: $time")
        numBombs = (gridSize * gridSize * (bombPercentage / 100)).roundToInt()
        binding.textviewPlayerName.text = playerName
        binding.textViewNumBombs.text = "$numBombs"
        binding.gridview.numColumns = gridSize
        milliSeconds = (gridSize * 40000).toLong()
        binding.buttonShowResults.setOnClickListener { showResults() }
    }

    private fun showResults() {
        gameFinished()
        finish()
    }

    private fun createLiveData() {
        viewModel = ViewModelProvider(this).get(GridModel::class.java)
        viewModel.gridModel(gridSize, numBombs)
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
    private fun checkControlTime(controlTime: String) {
        if (controlTime == "true") {
            binding.textViewCountDown.text = "${milliSeconds / countDownInterval} segundos"
            timer = object : CountDownTimer(milliSeconds, countDownInterval) {
                override fun onTick(millisUntilFinished: Long) {
                    binding.textViewCountDown.text =
                        "${millisUntilFinished / countDownInterval} segundos"
                    milliSeconds = millisUntilFinished
                }

                override fun onFinish() {
                    viewModel.showBombs()
                    startSound("gameOverSound")
                    resultData = "Resultado de la partida: Derrota.\n ¡Te has quedado sin tiempo!"
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
            if (result != "Ok") {
                if (viewModel.getLiveDataGridItems().value!![position].id == -1) {
                    startSound("bombSound")
                } else {
                    startSound("winnerSound")
                }
                resultData = result
                showPopUp()
            }
        }
    }

    private fun showPopUp() {
        timer?.cancel()
        gameFinished = true
        binding.buttonShowResults.visibility = View.VISIBLE
        val intent = Intent(this, PopUpFinishGame::class.java)
        val bundle = Bundle()
        bundle.putString("data", resultData)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun gameFinished() {
        val intent = Intent(this, ActivityResult::class.java)
        val bundle = Bundle()
        resultData += "\nAlias: $playerName\nTamaño de la cuadrícula: $gridSize x $gridSize\nNúmero de minas: $numBombs\nCasillas por descubrir: ${(gridSize * gridSize) - viewModel.countShowedSquares() - numBombs}\n"
        if (binding.textViewCountDown.visibility != View.GONE) {
            resultData += "Tiempo restante: ${binding.textViewCountDown.text}\n"
        }
        Log.i("ActivityGame", resultData)
        bundle.putString("result", resultData)
        intent.putExtras(bundle)
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