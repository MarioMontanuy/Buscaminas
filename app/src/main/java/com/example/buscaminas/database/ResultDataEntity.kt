package com.example.buscaminas.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "result_table")
data class ResultDataEntity (
    @PrimaryKey
    @ColumnInfo(name = "playerName") val playerName: String
/*@ColumnInfo(name = "date") val date: String,
    @Ignore
    @ColumnInfo(name = "gridSize") val gridSize: String,
    @Ignore
    @ColumnInfo(name = "bombPercentage") val bombPercentage: String,
    @Ignore
    @ColumnInfo(name = "bombNumber") val bombNumber: String,
    @Ignore
    @ColumnInfo(name = "timeLeft") val timeLeft: String,
    @Ignore
    @ColumnInfo(name = "squaresLeft") val squaresLeft: String,
    @ColumnInfo(name = "gameResult") val gameResult: String,*/

)
