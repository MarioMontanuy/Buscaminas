package com.example.buscaminas

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.collections.ArrayList

class GridModel() : ViewModel(), Parcelable {
    private var liveDataGridItems: MutableLiveData<ArrayList<GridItem>> = MutableLiveData()
    private var gridItems = ArrayList<GridItem>()
    private var gridSize: Int = 0
    private var numBombs: Int = 0
    private var firstClick: Boolean = true

    fun gridModel(gridSize: Int, numBombs: Int) {
        this.gridSize = gridSize
        this.numBombs = numBombs
        this.gridItems = getStartingData()
        liveDataGridItems.value = this.gridItems
    }

    fun getLiveDataGridItems(): LiveData<ArrayList<GridItem>> {
        return liveDataGridItems

    }

    private fun createGridItemValues(position: Int) {
        val random = Random(System.currentTimeMillis())
        var i = 0
        while (i < numBombs) {
            val bombNumber = random.nextInt(gridSize * gridSize)
            if (gridItems[bombNumber].id >= 0 && position != bombNumber) {
                gridItems[bombNumber].id = -1
                for (k in -1 until 2) {
                    if (bombNumber % gridSize != 0) {
                        addNumbersInRow(bombNumber + gridSize * k - 1, gridSize)
                    }
                    addNumbersInRow(bombNumber + gridSize * k, gridSize)
                    if (bombNumber % gridSize != gridSize - 1) {
                        addNumbersInRow(bombNumber + gridSize * k + 1, gridSize)
                    }
                }
                i++
            }
        }
    }

    private fun getStartingData(): ArrayList<GridItem> {
        val grid = ArrayList<GridItem>()
        for (i in 0 until gridSize * gridSize) {
            grid.add(GridItem())
        }
        return grid
    }

    private fun addNumbersInRow(currentNumber: Int, gridSize: Int) {
        if (currentNumber in 0 until gridSize * gridSize) {
            if (gridItems[currentNumber].id >= 0) {
                gridItems[currentNumber].id++
            }
        }
    }

    fun doAction(position: Int, click: String): String {
        var result = "Ok"
        if (firstClick) {
            createGridItemValues(position)
            firstClick = false
        }
        val currentItem = gridItems[position]
        if (click == "onItemClick") {
            result = onItemClickAction(position, currentItem)
        } else if (click == "onItemLongClick") {
            onItemLongClickAction(position)
        }
        liveDataGridItems.value = gridItems
        return result
    }

    private fun onItemClickAction(position: Int, currentItem: GridItem): String {
        if (!currentItem.flag) {
            currentItem.showed = true
            changeItemView(currentItem.id, position)
            if (currentItem.id == 0) {
                propagate(currentItem.id, position)
            }
            if (currentItem.id == -1) {
                showBombs()
                val square = Pair(position / gridSize, position % gridSize)
                return "Resultado de la partida: Derrota\nBomba activada en la posición $square"
            } else if (countShowedSquares() >= (gridSize * gridSize) - numBombs) {
                return "Resultado de la partida: Victoria\n¡Enhorabuena! Has conseguido evitar todas las bombas"
            }
        }
        return "Ok"
    }

    fun countShowedSquares(): Int {
        var counter = 0
        for (item in gridItems) {
            if (item.showed) {
                counter++
            }
        }
        return counter
    }

    private fun propagate(currentItemId: Int, position: Int) {
        if (currentItemId == 0) {
            for (k in -1 until 2) {
                updateColumn(position, position + gridSize * k - 1, "left")
                updateColumn(position, position + gridSize * k, "center")
                updateColumn(position, position + gridSize * k + 1, "right")
            }
        }
    }

    private fun updateColumn(position: Int, newPosition: Int, column: String) {
        if (isValidPosition(position, newPosition, column)) {
            val newItem = gridItems[newPosition]
            if (newItem.id >= 0) {
                if (!newItem.flag) {
                    changeItemView(newItem.id, newPosition)
                    newItem.showed = true
                    if (newItem.id == 0) {
                        onItemClickAction(newPosition, newItem)
                    }
                }
            }
        }
    }

    private fun isValidPosition(position: Int, newPosition: Int, column: String): Boolean {
        return ((column != "left" && position % gridSize == 0) || (column != "right" && position % gridSize == gridSize - 1) || column == "center" || position % gridSize in 1 until gridSize - 1) && newPosition in 0 until gridSize * gridSize && !gridItems[newPosition].showed
    }

    fun showBombs() {
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

    private fun onItemLongClickAction(position: Int) {
        if (gridItems[position].flag) {
            if (gridItems[position].showed) {
                changeItemView(gridItems[position].id, position)
            } else {
                gridItems[position].imageId = R.drawable.grid_layer
            }
        } else {
            gridItems[position].imageId = R.drawable.flag
        }
        gridItems[position].flag = !gridItems[position].flag
    }


    private fun changeItemView(item: Int, position: Int) {
        when (item) {
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

    constructor(parcel: Parcel) : this() {
        gridSize = parcel.readInt()
        numBombs = parcel.readInt()
        gridItems = parcel.readArrayList(GridModel::class.java.classLoader) as ArrayList<GridItem>
        liveDataGridItems.value = gridItems
    }

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