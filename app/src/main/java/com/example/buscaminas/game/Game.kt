package com.example.buscaminas.game

import com.example.buscaminas.R
import com.example.buscaminas.log.DataSingleton
import java.util.*
import kotlin.collections.ArrayList

class Game {
    private var gridItems = ArrayList<GridItem>()

    constructor(){
        this.gridItems = createStartingData()
    }

    private fun createStartingData(): ArrayList<GridItem> {
        val grid = ArrayList<GridItem>()
        for (i in 0 until DataSingleton.gridSize * DataSingleton.gridSize) {
            grid.add(GridItem())
        }
        return grid
    }

    fun createGridItemValues(position: Int) {
        val random = Random(System.currentTimeMillis())
        var i = 0
        while (i < DataSingleton.mineNumber) {
            val bombNumber = random.nextInt(DataSingleton.gridSize * DataSingleton.gridSize)
            if (gridItems[bombNumber].id >= 0 && position != bombNumber) {
                gridItems[bombNumber].id = -1
                for (k in -1 until 2) {
                    if (bombNumber % DataSingleton.gridSize != 0) {
                        addNumbersInRow(bombNumber + DataSingleton.gridSize * k - 1)
                    }
                    addNumbersInRow(bombNumber + DataSingleton.gridSize * k)
                    if (bombNumber % DataSingleton.gridSize != DataSingleton.gridSize - 1) {
                        addNumbersInRow(bombNumber + DataSingleton.gridSize * k + 1)
                    }
                }
                i++
            }
        }
    }

    private fun addNumbersInRow(currentNumber: Int) {
        if (currentNumber in 0 until DataSingleton.gridSize * DataSingleton.gridSize) {
            if (gridItems[currentNumber].id >= 0) {
                gridItems[currentNumber].id++
            }
        }
    }

    fun getGrid() : ArrayList<GridItem>{
        return this.gridItems
    }

    fun getCurrentItem(position: Int) : GridItem {
        return gridItems[position]
    }

    fun isFlag(position: Int) : Boolean{
        return gridItems[position].flag
    }

    fun itemShowed(position: Int) {
        gridItems[position].showed = true
    }

    fun getItemId(position: Int) : Int{
        return gridItems[position].id
    }
    fun isZero(position: Int) : Boolean {
        return gridItems[position].id == 0
    }

    fun isBomb(position: Int) : Boolean {
        return gridItems[position].id == -1
    }

    fun showBombs(){
        for (item in gridItems) {
            if (item.id == -1 && !item.showed) {
                if (item.flag) {
                    item.imageId = R.drawable.mine_flag
                } else {
                    item.imageId = R.drawable.mine_classic
                }
            }
        }
    }

    fun setLayerImage(position: Int) {
        gridItems[position].imageId = R.drawable.grid_layer
    }

    fun setFlagImage(position: Int) {
        gridItems[position].imageId = R.drawable.flag
    }

    fun changeFlag(position: Int){
        gridItems[position].flag = !gridItems[position].flag
    }

    fun isShowed(position: Int) : Boolean {
        return gridItems[position].showed
    }
    fun changeItemView(position: Int) {
        when (gridItems[position].id) {
            -1 -> gridItems[position].imageId = R.drawable.mine_red
            0 -> gridItems[position].imageId = R.drawable.empty_square
            1 -> gridItems[position].imageId = R.drawable.number1
            2 -> gridItems[position].imageId = R.drawable.number2
            3 -> gridItems[position].imageId = R.drawable.number3
            4 -> gridItems[position].imageId = R.drawable.number4
            5 -> gridItems[position].imageId = R.drawable.number5
            6 -> gridItems[position].imageId = R.drawable.number6
            7 -> gridItems[position].imageId = R.drawable.number7
            8 -> gridItems[position].imageId = R.drawable.number8
        }
    }

    fun countShowedSquares(): Int {
        var counter = 0
        for (item in gridItems) {
            if (item.showed && item.id != -1) {
                counter++
            }
        }
        return counter
    }
}