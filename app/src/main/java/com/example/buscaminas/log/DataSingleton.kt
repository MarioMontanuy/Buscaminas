package com.example.buscaminas.log

object DataSingleton {
    lateinit var playerName : String
    lateinit var currentTime : String
    var gridSize : Int = 0
    var bombPercentage : Double = 0.0
    var bombNumber : Int = 0
    lateinit var timeLeft : String
    var timeControl : Boolean = false
    var squaresLeft : Int = 0
    var gameResult : String = "Result"

    fun getResult(): String{
        var result = "Alias: $playerName\nTamaño de la cuadrícula: $gridSize x $gridSize\nNúmero de minas: $bombNumber\nCasillas por descubrir: $squaresLeft\n"
        if (timeControl){
            result += "Tiempo restante: $timeLeft"
        }
        return  result
    }
}