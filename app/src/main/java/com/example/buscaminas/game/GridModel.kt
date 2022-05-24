package com.example.buscaminas.game

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.buscaminas.log.DataSingleton
import kotlin.collections.ArrayList

class GridModel() : ViewModel(), Parcelable {
    private var liveDataGridItems: MutableLiveData<ArrayList<GridItem>> = MutableLiveData()
    private var game = Game()
    private var firstClick: Boolean = true

    fun gridModel() {
        liveDataGridItems.value = game.getGrid()
    }

    fun getLiveDataGridItems(): LiveData<ArrayList<GridItem>> {
        return liveDataGridItems

    }

    fun doAction(position: Int, click: String): String {
        var result = ""
        if (firstClick) {
            game.createGridItemValues(position)
            firstClick = false
        }
//        val currentItem = game.getCurrentItem(position)
        if (click == "onItemClick") {
            result = onItemClickAction(position)
        } else if (click == "onItemLongClick") {
            onItemLongClickAction(position)
        }
        liveDataGridItems.value = game.getGrid()
        return result
    }

    private fun onItemClickAction(position: Int) : String {
        if (!game.isFlag(position)) {
            game.itemShowed(position)
            game.changeItemView(position)
            if (game.isZero(position)) {
                propagate(position)
            }
            if (game.isBomb(position)) {
                val square = Pair(position / DataSingleton.gridSize, position % DataSingleton.gridSize)
                DataSingleton.gameResult = "Resultado de la partida: Derrota\nBomba activada en la posición $square"
                return "Bomb"
            } else if (game.countShowedSquares() >= (DataSingleton.gridSize * DataSingleton.gridSize) - DataSingleton.bombNumber) {
                DataSingleton.gameResult = "Resultado de la partida: Victoria\n¡Enhorabuena! Has conseguido evitar todas las bombas"
                return "Win"
            }
        }
        return ""
    }

    fun countShowedSquares() : Int{
       return game.countShowedSquares()
    }

    private fun propagate(position: Int) {
        if (game.isZero(position)) {
            for (k in -1 until 2) {
                updateColumn(position, position + DataSingleton.gridSize * k - 1, "left")
                updateColumn(position, position + DataSingleton.gridSize * k, "center")
                updateColumn(position, position + DataSingleton.gridSize * k + 1, "right")
            }
        }
    }

    private fun updateColumn(position: Int, newPosition: Int, column: String) {
        if (isValidPosition(position, newPosition, column)) {
            if (!game.isBomb(newPosition)) {
                if (!game.isFlag(newPosition)) {
                    game.changeItemView(newPosition)
                    game.itemShowed(newPosition)
                    if (game.isZero(newPosition)) {
                        onItemClickAction(newPosition)
                    }
                }
            }
        }
    }

    private fun isValidPosition(position: Int, newPosition: Int, column: String): Boolean {
        return ((column != "left" && position % DataSingleton.gridSize == 0) || (column != "right" && position % DataSingleton.gridSize == DataSingleton.gridSize - 1) || column == "center" || position % DataSingleton.gridSize in 1 until DataSingleton.gridSize - 1) && newPosition in 0 until DataSingleton.gridSize * DataSingleton.gridSize && !game.getCurrentItem(newPosition).showed
    }

    fun showBombs() {
        game.showBombs()
        liveDataGridItems.value = game.getGrid()
    }

    private fun onItemLongClickAction(position: Int) {
        if(!game.isShowed(position)){
            if (game.isFlag(position)) {
                game.setLayerImage(position)
            } else {
                game.setFlagImage(position)

            }
            game.changeFlag(position)
        }
    }

    constructor(parcel: Parcel) : this() {
        liveDataGridItems.value = game.getGrid()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
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