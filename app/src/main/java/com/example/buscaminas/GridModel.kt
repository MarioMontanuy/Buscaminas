package com.example.buscaminas

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.concurrent.TimeUnit

class GridModel() : ViewModel(), Parcelable {
    private var liveDataGridItems: MutableLiveData<ArrayList<GridItem>> = MutableLiveData()
    private var gridItems = ArrayList<GridItem>()
    private var gridSize: Int = 0
    private var numBombs: Int = 0



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
        var grid = getStartingData()
        println("Grid: $grid")
        // TODO numeros al azar
        val numbersRange = 0 until gridSize*gridSize
        var i = 0
        while(i < numBombs){
            val bombNumber = numbersRange.random()
            if (grid[bombNumber].id >= 0){
                grid[bombNumber].id = -1
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

    private fun getStartingData() : ArrayList<GridItem>{
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
        /*println("1- NUMERO DE CASILLAS ${squaresShowed.count()}")
        println("2- RESTO  ${(gridSize*gridSize)-numBombs}")*/
        val currentItem = gridItems[position]
        /*println("3- ITEM ID OBTENIDO: ${currentItem.id}")
        println("4- ITEM BANDERA OBTENIDO: ${currentItem.flag}")*/
        if(click == "onItemClick"){
            result = onItemClickAction(position, currentItem)
        } else if (click == "onItemLongClick"){
            onItemLongClickAction(position)
        }
        liveDataGridItems.value = gridItems
        return result
    }

    private fun onItemClickAction(position: Int, currentItem: GridItem) : String{
//        println("ON ITEM CLICK ACTION")
        if (!currentItem.flag){
//            println("BANDERA ES FALSE")
            currentItem.showed = true
            changeItemView(currentItem.id, position)
            if(currentItem.id == 0){
                propagate(currentItem.id, position)
            }
            if (currentItem.id == -1){
                showBombs()
                val square = Pair(position/gridSize, position%gridSize)
                return "Resultado de la partida: Derrota. \nBomba activada en la posición $square"
            }else if (countShowedSquares() >= (gridSize*gridSize)-numBombs){
                return "Resultado de la partida: Victoria. \n¡Enhorabuena! Has conseguido evitar todas las bombas"
            }
        }
        return "Ok"
    }
    // TODO check if countShowedSquares works instead of adding each time an item into a set to count it later
    fun countShowedSquares(): Int{
        var counter = 0
        for (item in gridItems){
            if (item.showed){
                counter++
            }
        }
        return counter
    }

    private fun propagate(currentItemId: Int, position: Int) {
        if (currentItemId == 0) {
//            println("ES UN 0")
            for (k in -1 until 2) {
                updateColumn(position,position + gridSize * k - 1, "left")
                updateColumn(position,position + gridSize * k, "center")
                updateColumn(position,position + gridSize * k + 1, "right")
            }
        }
    }

    private fun updateColumn(position: Int,newPosition: Int, column: String){
        /*println("---------------------------------")
        println("**posicion: $position")
        println("**newPosition: $newPosition")
        println("**column: $column")*/
        if (isValidPosition(position, newPosition, column)){
            //println("--------- VALIDO -----------")
            val newItem = gridItems[newPosition]
            if (newItem.id >= 0) {
                changeItemView(newItem.id, newPosition)
                newItem.showed = true
                if (newItem.id == 0) {
                    onItemClickAction(newPosition, newItem)
                }
            }
        }
    }

    private fun isValidPosition(position: Int, newPosition: Int, column: String): Boolean{
        return ((column != "left" && position % gridSize == 0) || (column != "right" && position % gridSize == gridSize - 1) || column == "center" || position % gridSize in 1 until gridSize - 1) && newPosition in 0 until gridSize * gridSize && !gridItems[newPosition].showed
    }

    fun showBombs(){
        // TODO si tiene una bandera: fondo verde, si es la bomba que he pulsado, fondo rojo, resto de bombas fondo gris (Falta poner las imagenes bien, la logica esta hecha)
        for (item in gridItems){
            if (item.id == -1 && !item.showed){
                if(item.flag){
                    item.imageId = R.drawable.bomb
                }else{
                    item.imageId = R.drawable.logo
                }
            }
        }
    }

    private fun onItemLongClickAction(position: Int){
        if (gridItems[position].flag){
            if(gridItems[position].showed){
                changeItemView(gridItems[position].id, position)
            }else{
                gridItems[position].imageId = R.drawable.capa_parrilla
            }
        }else{
            gridItems[position].imageId = R.drawable.bandera
        }
        gridItems[position].flag = !gridItems[position].flag
    }




    private fun changeItemView(item: Int, position: Int){
        when(item){
            // TODO aqui va la bomba de fondo rojo
            -1 -> gridItems[position].imageId = R.drawable.mina
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
    constructor(parcel: Parcel) : this() {
        gridSize = parcel.readInt()
        numBombs = parcel.readInt()
        gridItems = parcel.readArrayList(GridModel::class.java.classLoader) as ArrayList<GridItem>
        liveDataGridItems.value = gridItems
    }
    // Unused
   /* private fun countSquaresShowed(){
        var i = 0
        for (item in gridItems){
            if(item.showed){
                i++
            }
        }
        return i
    }*/

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(gridSize)
        parcel.writeInt(numBombs)
        parcel.writeList(gridItems)
    }

    companion object CREATOR : Parcelable.Creator<GridModel> {
        override fun createFromParcel(parcel: Parcel): GridModel {
            return GridModel(parcel)
        }

        override fun newArray(size: Int): Array<GridModel?> {
            return arrayOfNulls(size)
        }
    }
}