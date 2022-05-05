package com.example.buscaminas

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.AdapterView
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.buscaminas.databinding.ActivityGameBinding
import kotlin.math.roundToInt


class ActivityGame() : AppCompatActivity(), AdapterView.OnItemClickListener,
    AdapterView.OnItemLongClickListener {
    private lateinit var binding: ActivityGameBinding
    private var playerName = ""
    private var gridSize = 5
    private var numBombs= 0
    private var time = ""
    private var resultData = ""
    private var viewModel = GridModel()
    private var milliSeconds: Long = 180000
    private var countDownInterval: Long = 1000
    private var gridAdapter = GridAdapter(this, ArrayList())
    var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configLayout()
        if(savedInstanceState != null){
            milliSeconds = savedInstanceState.getLong("milliSeconds")
            countDownInterval = savedInstanceState.getLong("countDownInterval")
            viewModel = savedInstanceState.getParcelable("viewModel")!!
        }else{
            createLiveData()
        }
        setAdapter()
        createObserver()
        checkControlTime(time)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        println("onSaveInstanceState")
        outState.putLong("milliSeconds", milliSeconds)
        outState.putLong("countDownInterval", countDownInterval)
        outState.putParcelable("viewModel", viewModel)
        //outState.putBinder("chronometer", timer)
        // TODO guardar ViewModel
    }

    private fun configLayout(){

        playerName = intent.getStringExtra("playerName").toString()
        gridSize = intent.getIntExtra("gridSize", 5)
        val bombPercentage = intent.getDoubleExtra("bombPercentage", 15.0)
        time = intent.getStringExtra("time").toString()
        numBombs = (gridSize * gridSize * (bombPercentage / 100)).roundToInt()
        binding.textviewPlayerName.text = "Jugador: $playerName\t\tBombas: $numBombs"
        binding.gridview.numColumns = gridSize
        // TODO establecer un tamaño fijo para el gridview
        println("PORCENTAJE DE BOMBAS: $bombPercentage")
        println("RESULTADO: ${(bombPercentage/100)}")
        println("BOMBAS*** $numBombs")
    }

    private fun createLiveData(){
        viewModel= ViewModelProvider(this).get(GridModel::class.java)
        viewModel.gridModel(gridSize, numBombs)
    }
    private fun createObserver(){
        val observer: Observer<ArrayList<GridItem>> =
            Observer {   // Update the UI, in this case, a TextView.
                println("MODIFICACION CAPTURADA")
                gridAdapter.notifyDataSetChanged()
            }
        viewModel.getLiveDataGridItems().observe(this, observer)
    }

    private fun setAdapter(){
        gridAdapter = GridAdapter(this, viewModel.getLiveDataGridItems().value!!)
        binding.gridview.adapter = gridAdapter
        binding.gridview.onItemClickListener = this
        binding.gridview.onItemLongClickListener = this
    }
    // Unused
    /*private fun getItemsForGridView(): ArrayList<Int>{
        val grid = ArrayList<Int>()
        for (i in 0 until gridSize*gridSize) {
            grid.add(R.drawable.capa_parrilla)
        }
        return grid
    }*/

    private fun checkControlTime(controlTime: String){
        println("Control time")
        if (controlTime == "true"){
            println("TRUEE")
            // TODO asignar un objeto al timer
           timer = object : CountDownTimer(milliSeconds, countDownInterval) {
                override fun onTick(millisUntilFinished: Long) {
                    binding.textViewCountDown.text="Tiempo restante: ${millisUntilFinished/countDownInterval} segundos"
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


    // Unused
    /*private fun addGridPos(grid: ArrayList<GridItem>): ArrayList<GridItem>{
        var x = 0
        var y = 0
        for(k in 0 until gridSize*gridSize){
            if (x < gridSize){
                grid[k].gridPos=Pair(x,y)
            }else{
                x = 0
                y++
            }
        }
        return grid
    }*/



    override fun onItemClick(grid: AdapterView<*>, view: View, position: Int, id: Long) {
//        Toast.makeText(this, "CLICK", Toast.LENGTH_SHORT).show()
        // TODO sombrear las casillas para no poner el margen del grid y que se vea de por si
        val result = viewModel.doAction(position, "onItemClick")
        if (result != "Ok"){
            resultData = result
            showPopUp()
            println("Final")
        }
    }

    /*private fun propagate(currentItemId: Int, position: Int){
        if (currentItemId == 0){
            println("ES UN 0")
            var newItem: GridItem
            for(k in -1 until 2){

                if (position % gridSize != 0 && position+gridSize*k-1 in 0 until gridSize*gridSize){
                    newItem = gridItemsValues[position+gridSize*k-1]
                    if(newItem.id >= 0){
                        if(newItem.id == 0 && ){
                            propagate(newItem.id,position+gridSize*k-1)
                        }
                        changeItemView(newItem.id, position+gridSize*k-1)
                    }
                }
                if(position+gridSize*k in 0 until gridSize*gridSize){
                    newItem = gridItemsValues[position+gridSize*k]
                    if(newItem.id >= 0) {
                        if(newItem.id == 0){
                            propagate(newItem.id,position+gridSize*k)
                        }
                        changeItemView(newItem.id, position + gridSize * k)
                    }
                }
                if (position % gridSize != gridSize-1 && position+gridSize*k+1 in 0 until gridSize*gridSize){
                    newItem = gridItemsValues[position+gridSize*k+1]
                    if(newItem.id >= 0) {
                        if(newItem.id == 0){
                            propagate(newItem.id,position+gridSize*k+1)
                        }
                        changeItemView(newItem.id, position + gridSize * k + 1)
                    }
                }
            }
        }*/

    // Unused
    /*private fun changeImage(item: Int, image: ImageView){
        when(item){
            -1 -> image.setImageResource(R.drawable.mina)
            0 -> image.setImageResource(R.drawable.number0)
            1 -> image.setImageResource(R.drawable.number1)
            2 -> image.setImageResource(R.drawable.number2)
            3 -> image.setImageResource(R.drawable.number3)
            4 -> image.setImageResource(R.drawable.number4)
            5 -> image.setImageResource(R.drawable.number5)
            6 -> image.setImageResource(R.drawable.number6)
            7 -> image.setImageResource(R.drawable.number7)
            8 -> image.setImageResource(R.drawable.number8)
        }
    }*/



    private fun showPopUp(){
        timer?.cancel()
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
        bundle.putString("logData", "Alias: $playerName.\nTamaño de la cuadrícula: $gridSize x $gridSize.\nNúmero de minas: $numBombs \n${binding.textViewCountDown.text}.\nCasillas por descubrir: ${(gridSize*gridSize)-viewModel.squaresShowed.count()}.\n")
        bundle.putString("result", result)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onItemLongClick(grid: AdapterView<*>, view: View, position: Int, id: Long): Boolean {
        // Toast.makeText(this, "LONG CLICK", Toast.LENGTH_SHORT).show()
        viewModel.doAction(position, "onItemLongClick")
        return true
    }
}

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