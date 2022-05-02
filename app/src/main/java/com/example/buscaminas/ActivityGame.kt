package com.example.buscaminas

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.AdapterView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import com.example.buscaminas.databinding.ActivityGameBinding
import kotlin.math.roundToInt


class ActivityGame: AppCompatActivity(), AdapterView.OnItemClickListener,
    AdapterView.OnItemLongClickListener {
    private lateinit var binding: ActivityGameBinding

    private var playerName = ""
    private var gridSize = 5
    private var numBombs= 0
    private var squaresShowed = mutableSetOf<Long>()
    private var resultData = ""
    private var bandera = false
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
        binding.textviewPlayerName.text = "Jugador: $playerName\t\tBombas: $numBombs"
        binding.gridview.numColumns = gridSize
        binding.gridview.scaleY = 0.9F
        val gridItems = getStartingGrid()
        println("GRID ITEMS: $gridItems")
        val gridAdapter = GridAdapter(this, gridItems)
        binding.gridview.adapter = gridAdapter
        binding.gridview.onItemClickListener = this
        binding.gridview.onItemLongClickListener = this
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
                    println("FINISHH")
                    resultData = "Resultado de la partida: Derrota.\n ¡Te has quedado sin tiempo!"
                    showPopUp()
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
//        Toast.makeText(this, "CLICK", Toast.LENGTH_SHORT).show()
        // TODO sombrear las casillas para no poner el margen del grid y que se vea de por si
        // TODO cambiar la forma de comprobar la bandera
        println("GRID ITEM -> "+grid.getItemAtPosition(position))
        println("GRID --> $grid")
        println("VIEW --> $view")
        println("ID --> $id")
        println("NUMERO DE CASILLAS ${squaresShowed.count()}")
        println("RESTO  ${(gridSize*gridSize)-numBombs}")
        println("BANDERA: ${grid.adapter.getItem(position)}")
        //if(grid.adapter.getItem(position) == 0){
        grid.adapter.getView(position, view, grid)
        squaresShowed.add(id)
        println("BANDERA = 0")
        if (grid.getItemAtPosition(position) == -1){
            val square = Pair(position/gridSize, position%gridSize)
            //mostrar pop up
            // opción con una nueva activity
            resultData = "Resultado de la partida: Derrota. \nBomba activada en la posición $square"
            showPopUp()
            // mostrar alertDialog
            /*val alert = AlertDialog.Builder(this)
            .setCancelable(false)
            val popUpView = layoutInflater.inflate(R.layout.popup_finishgame, null)
            val acceptButton = popUpView.findViewById<Button>(R.id.acceptButton)
            val textBomb = popUpView.findViewById<TextView>(R.id.textBomb)
            val textSquaresLeft = popUpView.findViewById<TextView>(R.id.textSquaresLeft)
            textBomb.text = "Vaaya! Parece que te has topado con una bomba en la posición $square"
            textSquaresLeft.text = "Casillas por descubrir: ${(gridSize*gridSize)-squaresShowed.count()}"
            alert.setView(popUpView)
            val alertView = alert.create()
            alertView.show()
            acceptButton.setOnClickListener{
                gameFinished("Resultado de la partida: Derrota. \n Bomba activada en la posición $square")
                //finish()
            }*/
            println("Final")


            //paso a pantalla final
            //final
        }else if (squaresShowed.count() >= (gridSize*gridSize)-numBombs){
            resultData = "Resultado de la partida: Victoria. \n¡Enhorabuena! Has conseguido evitar todas las bombas"
            showPopUp()
        }
       // }
    }

    private fun showPopUp(){
        val intent = Intent(this, PopUpFinishGame::class.java)
        println("result DATA $resultData")
        val bundle = Bundle()
        bundle.putString("data", resultData)
        intent.putExtras(bundle)
        resultLauncher.launch(intent)
    }

    private var resultLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            gameFinished(resultData)
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

    override fun onItemLongClick(grid: AdapterView<*>, view: View, position: Int, id: Long): Boolean {
        // Toast.makeText(this, "LONG CLICK", Toast.LENGTH_SHORT).show()
        // TODO evitar que se pueda dar click en una casilla si está la bandera puesta (ahora esta con el getItem, funciona pero no es muy correcto)
        grid.adapter.getView(position+gridSize*gridSize, view, grid)
        return true
    }
}