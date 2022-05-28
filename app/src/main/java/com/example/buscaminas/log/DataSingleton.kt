package com.example.buscaminas.log

import com.example.buscaminas.database.db.roomexample.GameResult

object DataSingleton {
    var playerName : String = ""
    var currentTime : String= ""
    var gridSize : Int = 0
    var minePercentage : Double = 0.0
    var mineNumber : Int = 0
    var timeLeft : String = ""
    var timeControl : Boolean = false
    var squaresLeft : Int = 0
    var mineSquare : String = ""
    var gameResult : String = ""

    fun setDefaultValues(){
        this.playerName = ""
        this.currentTime = ""
        this.gridSize = 0
        this.minePercentage = 0.0
        this.mineNumber = 0
        this.timeLeft = ""
        this.timeControl = false
        this.squaresLeft = 0
        this.mineSquare = ""
        this.gameResult = ""
    }

    fun getResult(): String{
        var result = "Alias: $playerName\nTamaño de la cuadrícula: $gridSize x $gridSize\nNúmero de minas: $mineNumber\nCasillas por descubrir: $squaresLeft\n"
        if (mineSquare != ""){
            result += "Bomba activada en la posición $mineSquare\n"
        }
        if (timeControl){
            result += "Tiempo restante: $timeLeft\n"
        }
        result += "Resultado de la partida: $gameResult\n"
        return  result
    }

    fun getResultForPopUp() : String {
        var result = "Resultado de la partida: $gameResult\n"
        result += if (gameResult == "Victoria"){
            "¡Enhorabuena! Has conseguido evitar todas las bombas"
        }else if (mineSquare != ""){
            "Bomba activada en la posición $mineSquare"
        }else{
            "¡Te has quedado sin tiempo!"
        }
        return result
    }

    var currentGame: GameResult? = null
}