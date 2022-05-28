package com.example.buscaminas.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_result_table")
class GameResult
    (
    @ColumnInfo(name = "playerName") val playerName: String,
    @ColumnInfo(name = "gameDate") val gameDate: String,
    @ColumnInfo(name = "gridSize") val gridSize: Int,
    @ColumnInfo(name = "minePercentage") val minePercentage: Double,
    @ColumnInfo(name = "mineNumber") val mineNumber: Int,
    @ColumnInfo(name = "timeLeft") val timeLeft: String,
    @ColumnInfo(name = "timeControl") val timeControl: Boolean,
    @ColumnInfo(name = "squaresLeft") val squaresLeft: Int,
    @ColumnInfo(name = "mineSquare") val mineSquare: String,
    @ColumnInfo(name = "gameResult") val gameResult: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private var id: Int = 0

    fun getId() : Int {
        return this.id
    }

    fun setId(id : Int) {
        this.id = id
    }

    fun getDetailedData() : String{
        var result = "Nombre del jugador: $playerName\nFecha: $gameDate\nTamaño de cuadrícula: $gridSize\nPorcentaje de minas: $minePercentage\nNúmero de minas: $mineNumber\nCasillas restantes: $squaresLeft\n"
        result += if (gameResult == "Victoria"){
            "¡Enhorabuena! Has conseguido evitar todas las bombas\n"
        }else if (mineSquare != ""){
            "Bomba activada en la posición $mineSquare\n"
        }else{
            "¡Te has quedado sin tiempo!\n"
        }
        if (timeControl){
            result += "Tiempo restante: $timeLeft\n"
        }
        result += "Resultado de la partida: $gameResult\n"
        return result
    }
}