package com.example.buscaminas.log

import com.example.buscaminas.database.GameResult
import java.text.SimpleDateFormat
import java.util.*

object DataSingleton {
    var playerName: String = ""
    var currentTime: Date = Date()
    var gridSize: Int = 0
    var minePercentage: Double = 0.0
    var mineNumber: Int = 0
    var timeLeft: String = ""
    var timeControl: Boolean = false
    var squaresLeft: Int = 0
    var mineSquare: String = ""
    var gameResult: String = ""
    var currentSquare: String = ""
    var currentTimeLeft: String = ""

    fun setDefaultValues() {
        this.playerName = ""
        this.currentTime = Date()
        this.gridSize = 0
        this.minePercentage = 0.0
        this.mineNumber = 0
        this.timeLeft = ""
        this.timeControl = false
        this.squaresLeft = 0
        this.mineSquare = ""
        this.gameResult = ""
        this.currentSquare = ""
        this.currentTimeLeft = ""
    }

    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("es", "ES"))


    fun getResult(): String {
        var result =
            "Alias: $playerName\nTamaño de la cuadrícula: $gridSize x $gridSize\nNúmero de minas: $mineNumber\nCasillas por descubrir: $squaresLeft\n"
        if (mineSquare != "") {
            result += "Bomba activada en la posición $mineSquare\n"
        }
        result += if (timeControl) {
            "Tiempo restante: $timeLeft\n"
        } else {
            "Control de tiempo desactivado\n"
        }
        result += "Resultado de la partida: $gameResult\n"
        return result
    }

    fun getLogData(): String {
        var result =
            "Alias: $playerName\nTamaño de la cuadrícula: $gridSize x $gridSize\nPorcentaje de minas: $minePercentage\nNúmero de minas: $mineNumber\n"
        result += if (timeControl) {
            "Control de tiempo activado\n"
        } else {
            "Control de tiempo desactivado\n"
        }
        return result
    }

    fun getLogDataCurrentClick(): String {
        var result = "Casilla seleccionada: $currentSquare\n"
        if (timeControl) {
            result += "Tiempo restante: $currentTimeLeft\n"
        }
        return result
    }

    fun getResultForPopUp(): String {
        var result = "Resultado de la partida: $gameResult\n"
        result += if (gameResult == "Victoria") {
            "¡Enhorabuena! Has conseguido evitar todas las bombas"
        } else if (mineSquare != "") {
            "Bomba activada en la posición $mineSquare"
        } else {
            "¡Te has quedado sin tiempo!"
        }
        return result
    }

    var currentGame: GameResult? = null
}