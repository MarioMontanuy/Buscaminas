package com.example.buscaminas

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.example.buscaminas.databinding.ActivityGameBinding
import com.example.buscaminas.databinding.GridItemBinding


class ActivityGame: AppCompatActivity(), AdapterView.OnItemClickListener {
    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val playerName = intent.getStringExtra("playerName")
        val gridSize = intent.getIntExtra("gridSize", 5)
        val time = intent.getStringExtra("time")
        val bombPercentage = intent.getDoubleExtra("bombPercentage", 15.0)

        println("playerName $playerName")
        println("gridSize $gridSize")
        println("time $time")
        println("bombPercentage $bombPercentage")
        binding.textviewPlayerName.text = playerName
        /*if (time == "true"){
            binding.textviewTime = 180
            binding.textviewTime.incrementProgressBy(1)
        }else{
            binding.progressBar3.
        }*/
        binding.gridview.numColumns = gridSize
        val gridItems = getStartingGrid(gridSize, bombPercentage)
        println("---GRID----$gridItems")
        val gridAdapter = GridAdapter(this, gridItems)
        binding.gridview.adapter = gridAdapter
        binding.gridview.onItemClickListener = this
    }

    private fun getStartingGrid(gridSize: Int, bombPercentage: Double): ArrayList<Int> {
        var grid = ArrayList<Int>()
        for (i in 0 until gridSize*gridSize) {
            grid.add(0)
        }
        // Añadir bombas
        val numBombs = gridSize*gridSize*(bombPercentage/100)
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
    override fun onItemClick(grid: AdapterView<*>, view: View, index: Int, id: Long) {
        Toast.makeText(this, "CLICK", Toast.LENGTH_SHORT).show()
        println("GRID ITEM -> "+grid.getItemAtPosition(index))
        println("GRID --> $grid")
        println("VIEW --> $view")
        grid.adapter.getView(index, view, grid)
    }
}