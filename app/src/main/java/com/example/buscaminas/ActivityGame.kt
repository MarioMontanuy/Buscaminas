package com.example.buscaminas

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.buscaminas.databinding.ActivityGameBinding
import kotlin.math.roundToInt


class ActivityGame: AppCompatActivity(), AdapterView.OnItemClickListener {
    private lateinit var binding: ActivityGameBinding

    private var playerName = ""
    private var gridSize = 5
    private var numBombs= 0
    private var squaresShowed = mutableSetOf<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDataFromConfigAndSetUp()
        checkControlTime(intent.getStringExtra("time").toString())
    }
    private fun getDataFromConfigAndSetUp(){
        // Get data
        playerName = intent.getStringExtra("playerName").toString()
        gridSize = intent.getIntExtra("gridSize", 5)
        val bombPercentage = intent.getDoubleExtra("bombPercentage", 15.0)
        // Set up
        println("PORCENTAJE DE BOMBAS: $bombPercentage")
        println("RESULTADO: ${(bombPercentage/100)}")
        numBombs = (gridSize * gridSize * (bombPercentage / 100)).roundToInt()
        println("BOMBAS*** $numBombs")
        binding.textviewPlayerName.text = "Jugador: $playerName"
        binding.gridview.numColumns = gridSize
        binding.gridview.scaleY = 0.9F
        val gridItems = getStartingGrid()
        val gridAdapter = GridAdapter(this, gridItems)
        binding.gridview.adapter = gridAdapter
        binding.gridview.onItemClickListener = this
    }

    private fun checkControlTime(controlTime: String){
        println("Control time")
        if (controlTime == "true"){
            println("TRUEE")
            object : CountDownTimer(180000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    binding.textViewCountDown.text="Tiempo restante: ${millisUntilFinished/1000} segundos"
                }
                override fun onFinish() {
                    gameFinished("Resultado de la partida: Derrota. \n ¡Te has quedado sin tiempo!\"")
                }
            }.start()
        }else{
            binding.textViewCountDown.visibility = View.GONE
        }
    }
    private fun getStartingGrid(): ArrayList<Int> {
        var grid = ArrayList<Int>()
        for (i in 0 until gridSize*gridSize) {
            grid.add(0)
        }
        // Añadir bombas
        // TODO numeros al azar
        // var top: Int=(0..3).shuffled().first()
        val numbersRange = 0 until gridSize*gridSize
        var i = 0
        while(i < numBombs){
            val bombNumber = numbersRange.random()
            if (grid[bombNumber] >= 0){
                grid[bombNumber] = -1
                // Añadir numeros
                for(k in -1 until 2){
                    if (bombNumber % gridSize != 0){
                        grid = addNumbersInRow(bombNumber+gridSize*k-1, grid, gridSize)
                    }
                    grid = addNumbersInRow(bombNumber+gridSize*k, grid, gridSize)
                    if (bombNumber % gridSize != gridSize-1){
                        grid = addNumbersInRow(bombNumber+gridSize*k+1, grid, gridSize)
                    }
                }
                i++
            }
        }
        return grid
    }

    private fun addNumbersInRow(currentNumber: Int, grid: ArrayList<Int>, gridSize: Int): ArrayList<Int> {
        if (currentNumber in 0 until gridSize*gridSize){
            if (grid[currentNumber] >= 0){
                grid[currentNumber]++
            }
        }
        return grid
    }
    override fun onItemClick(grid: AdapterView<*>, view: View, position: Int, id: Long) {
        Toast.makeText(this, "CLICK", Toast.LENGTH_SHORT).show()
        println("GRID ITEM -> "+grid.getItemAtPosition(position))
        println("GRID --> $grid")
        println("VIEW --> $view")
        println("ID --> $id")
        squaresShowed.add(id)
        println("NUMERO DE CASILLAS ${squaresShowed.count()}")
        println("RESTO  ${(gridSize*gridSize)-numBombs}")
        // TODO sombrear las casillas para no poner el margen del grid y que se vea de por si
        // Update square in position=index
        grid.adapter.getView(position, view, grid)
        if (grid.getItemAtPosition(position) == -1){
            val square: Pair<Int, Int> = Pair(position/gridSize, position%gridSize)
            gameFinished("Resultado de la partida: Derrota. \n Bomba activada en la posición $square")
            finish()
            //mostrar pop up
                // opción con una nueva activity
            /*val intent = Intent(this, PopUpFinishGame::class.java)
            startActivity(intent)*/

                // mostrar alertDialog
            /*val alert = AlertDialog.Builder(this)
            .setCancelable(false)
            val popUpView = layoutInflater.inflate(R.layout.popup_finishgame, null)
            val acceptButton = popUpView.findViewById<Button>(R.id.acceptButton)
            val textBomb = popUpView.findViewById<TextView>(R.id.textBomb)
            val textSquaresLeft = popUpView.findViewById<TextView>(R.id.textSquaresLeft)
            textBomb.text = "Vaaya! Parece que te has topado con una bomba en la posición $square"
            textSquaresLeft.text = "Han quedado $squaresLeft casillas sin descubrir"
            alert.setView(popUpView)
            val alertView = alert.create()
            alertView.show()
            acceptButton.setOnClickListener{
                finish()
            }*/
            println("Final")
            //paso a pantalla final
            //final
        }

        if (squaresShowed.count() >= (gridSize*gridSize)-numBombs){
            gameFinished("Resultado de la partida: Victoria. \n ¡Enhorabuena! Has conseguido evitar todas las bombas")
            finish()
        }
    }

    private fun gameFinished(result: String){
        val intent = Intent(this, ActivityResult::class.java)
        val bundle = Bundle()
        bundle.putString("logData", "Alias: $playerName.\nTamaño de la cuadrícula: $gridSize x $gridSize.\nNúmero de minas: $numBombs %\n${binding.textViewCountDown.text}.\nCasillas por descubrir: ${(gridSize*gridSize)-squaresShowed.count()}.\n")
        bundle.putString("result", result)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}