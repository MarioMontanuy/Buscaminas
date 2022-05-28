package com.example.buscaminas.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.buscaminas.R
import com.example.buscaminas.activities.ActivityResult
import com.example.buscaminas.extra.PopUpFinishGame
import com.example.buscaminas.game.GridAdapter
import com.example.buscaminas.game.GridItem
import com.example.buscaminas.game.GridModel
import com.example.buscaminas.log.DataSingleton
import com.example.buscaminas.service.SoundService
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt


class GameFragment : Fragment(), AdapterView.OnItemClickListener,
    AdapterView.OnItemLongClickListener  {

    private var currentView: View? = null
    private var resultData = ""
    private var viewModel = GridModel()
    private var milliSeconds: Long = 180000
    private var countDownInterval: Long = 1000
    private var gridAdapter = GridAdapter(context, ArrayList())
    private var gameFinished = false
    private var timer: CountDownTimer? = null
    private var gridView: GridView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        currentView = inflater.inflate(R.layout.fragment_game, container, false)
        configLayout()
        if (savedInstanceState != null) {
            milliSeconds = savedInstanceState.getLong("milliSeconds")
            viewModel = savedInstanceState.getParcelable("viewModel")!!
            gameFinished = savedInstanceState.getBoolean("gameFinished")
            resultData += savedInstanceState.getString("resultData")
            currentView?.findViewById<TextView>(R.id.textViewSquaresLeft)?.text = DataSingleton.squaresLeft.toString()
        } else {
            createLiveData()
            DataSingleton.squaresLeft = (DataSingleton.gridSize * DataSingleton.gridSize) - viewModel.countShowedSquares() - DataSingleton.mineNumber
            currentView?.findViewById<TextView>(R.id.textViewSquaresLeft)?.text = DataSingleton.squaresLeft.toString()
        }
        setAdapter()
        createObserver()
        checkControlTime()
        if (gameFinished) {
            currentView?.findViewById<Button>(R.id.buttonShowResults)?.visibility = View.VISIBLE
            timer?.cancel()
        }
        return currentView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("viewModel", viewModel)
        outState.putLong("milliSeconds", milliSeconds)
        outState.putBoolean("gameFinished", gameFinished)
        outState.putString("resultData", resultData)
    }

    private fun configLayout() {
        val preferences = context?.getSharedPreferences("com.example.buscaminas_preferences", Context.MODE_PRIVATE)
        DataSingleton.playerName = preferences?.getString("playerName", "Jugador").toString()
        DataSingleton.gridSize = preferences?.getString("preferenceGridSize", "5")!!.toInt()
        DataSingleton.minePercentage = preferences.getString("preferenceBombPercentage", "15")!!.toDouble()
        DataSingleton.timeControl = preferences.getBoolean("time", false)
        DataSingleton.mineNumber = (DataSingleton.gridSize * DataSingleton.gridSize * (DataSingleton.minePercentage / 100)).roundToInt()
        currentView?.findViewById<TextView>(R.id.textviewPlayerName)?.text = DataSingleton.playerName
        currentView?.findViewById<TextView>(R.id.textViewNumBombs)?.text = DataSingleton.mineNumber.toString()
        gridView = currentView?.findViewById(R.id.gridview)
        gridView?.numColumns = DataSingleton.gridSize
        milliSeconds = (DataSingleton.gridSize * 40000).toLong()
        currentView?.findViewById<Button>(R.id.buttonShowResults)?.setOnClickListener { showResults() }
    }

    private fun showResults() {
        gameFinished()
        activity?.finish()
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
        viewModel.getLiveDataGridItems().observe(viewLifecycleOwner, observer)
    }

    private fun setAdapter() {
        gridAdapter = GridAdapter(context, viewModel.getLiveDataGridItems().value!!)
        gridView?.adapter = gridAdapter
        gridView?.onItemClickListener = this
        gridView?.onItemLongClickListener = this
    }

    @SuppressLint("SetTextI18n")
    private fun checkControlTime() {
        val textViewCountDown = currentView?.findViewById<TextView>(R.id.textViewCountDown)
        if (DataSingleton.timeControl) {
            textViewCountDown?.text = "${milliSeconds / countDownInterval}"
            timer = object : CountDownTimer(milliSeconds, countDownInterval) {
                override fun onTick(millisUntilFinished: Long) {
                    textViewCountDown?.text =
                        "${millisUntilFinished / countDownInterval}"
                    milliSeconds = millisUntilFinished
                }

                override fun onFinish() {
                    startSound("gameOverSound")
                    DataSingleton.gameResult = "Derrota"
                    showPopUp()
                }
            }.start()
        } else {
            textViewCountDown?.visibility = View.GONE
            currentView?.findViewById<ImageView>(R.id.imageViewCountDown)?.visibility = View.GONE
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
        currentView?.findViewById<TextView>(R.id.textViewSquaresLeft)?.text = DataSingleton.squaresLeft.toString()
    }

    private fun showPopUp() {
        timer?.cancel()
        gameFinished = true
        viewModel.showBombs()
        currentView?.findViewById<Button>(R.id.buttonShowResults)?.visibility = View.VISIBLE

        val intent = Intent(context, PopUpFinishGame::class.java)
        /* val bundle = Bundle()
         bundle.putString("data", resultData)
         intent.putExtras(bundle)*/
        startActivity(intent)
    }

    private fun gameFinished() {
        val intent = Intent(context, ActivityResult::class.java)
        /*val bundle = Bundle()
        resultData += "\nAlias: $playerName\nTamaño de la cuadrícula: $gridSize x $gridSize\nNúmero de minas: $numBombs\nCasillas por descubrir: ${(gridSize * gridSize) - viewModel.countShowedSquares() - numBombs}\n"
        if (binding.textViewCountDown.visibility != View.GONE) {
            resultData += "Tiempo restante: ${binding.textViewCountDown.text}\n"
        }

        Log.i("ActivityGame", resultData)
        bundle.putString("result", resultData)
        intent.putExtras(bundle)*/
        DataSingleton.squaresLeft = (DataSingleton.gridSize * DataSingleton.gridSize) - viewModel.countShowedSquares() - DataSingleton.mineNumber
        DataSingleton.timeLeft = currentView?.findViewById<TextView>(R.id.textViewCountDown)?.text.toString()
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
        val intentService = Intent(context, SoundService::class.java)
        intentService.putExtra("sound", sound)
        context?.startService(intentService)
    }
}