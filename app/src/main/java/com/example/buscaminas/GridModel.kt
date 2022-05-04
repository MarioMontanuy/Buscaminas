package com.example.buscaminas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.collections.ArrayList

class GridModel: ViewModel() {
    private var liveDataGridItems: MutableLiveData<ArrayList<GridItem>> = MutableLiveData()
    private var gridItems = ArrayList<GridItem>()

    private var gridSize: Int = 0
    private var numBombs: Int = 0
    var squaresShowed = mutableSetOf<Int>()

    fun gridModel(gridSize: Int, numBombs: Int){
        this.gridSize = gridSize
        this.numBombs = numBombs
        this.gridItems = getStartingGridItemValues()
        println("GRID ITEMS: $gridItems")
        liveDataGridItems.value = this.gridItems
        println("LIVEDATA: ${liveDataGridItems.value}")
    }

    fun getLiveDataGridItems() : LiveData<ArrayList<GridItem>> {
        return liveDataGridItems
    }

    private fun getStartingGridItemValues(): ArrayList<GridItem> {
        var grid = getStartingData(gridSize)
        //grid = addGridPos(grid)
        println("Grid: $grid")
        // Añadir bombas
        // TODO numeros al azar
        val numbersRange = 0 until gridSize*gridSize
        var i = 0
        while(i < numBombs){
            val bombNumber = numbersRange.random()
            if (grid[bombNumber].id >= 0){
                grid[bombNumber].id = -1
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

    private fun getStartingData(gridSize: Int) : ArrayList<GridItem>{
        val grid = ArrayList<GridItem>()
        for (i in 0 until gridSize*gridSize) {
            grid.add(GridItem())
        }
        return grid
    }

    private fun addNumbersInRow(currentNumber: Int, grid: ArrayList<GridItem>, gridSize: Int): ArrayList<GridItem> {
        if (currentNumber in 0 until gridSize*gridSize){
            if (grid[currentNumber].id >= 0){
                grid[currentNumber].id++
            }
        }
        return grid
    }
    fun doAction(position: Int, click: String): String{
        var result = "Ok"
        println("1- NUMERO DE CASILLAS ${squaresShowed.count()}")
        println("2- RESTO  ${(gridSize*gridSize)-numBombs}")
        val currentItem = gridItems[position]
        println("3- ITEM ID OBTENIDO: ${currentItem.id}")
        println("4- ITEM BANDERA OBTENIDO: ${currentItem.flag}")
        if(click == "onItemClick"){
            if (!currentItem.flag){
                println("BANDERA ES FALSE")
                changeItemView(currentItem.id, position)
                //propagate(currentItem.id, position)
                squaresShowed.add(position)

                if (currentItem.id == -1){
                    val square = Pair(position/gridSize, position%gridSize)
                    result = "Resultado de la partida: Derrota. \nBomba activada en la posición $square"
                }
                if (squaresShowed.count() >= (gridSize*gridSize)-numBombs){
                    result = "Resultado de la partida: Victoria. \n¡Enhorabuena! Has conseguido evitar todas las bombas"
                }
            }
        }
        if (click == "onItemLongClick"){
            if (gridItems[position].flag){
                gridItems[position].imageId = R.drawable.capa_parrilla
            }else{
                gridItems[position].imageId = R.drawable.bandera
            }
            gridItems[position].flag = !gridItems[position].flag
        }
        liveDataGridItems.value = gridItems
        return result
    }

    private fun changeItemView(item: Int, position: Int){
        when(item){
            -1 -> gridItems[position].imageId = R.drawable.bomb
            0 -> gridItems[position].imageId = R.drawable.number0
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
}