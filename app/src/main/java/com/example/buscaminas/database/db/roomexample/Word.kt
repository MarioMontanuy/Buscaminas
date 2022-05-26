package com.example.buscaminas.database.db.roomexample

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
class Word(
    @PrimaryKey @ColumnInfo(name = "playerName") val playerName: String
    )
