package com.example.buscaminas.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GameResultDao {

    @Query("SELECT * FROM game_result_table ORDER BY playerName ASC")
    fun getAlphabetizedWords(): Flow<List<GameResult>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(gameResult: GameResult)

    @Query("DELETE FROM game_result_table")
    suspend fun deleteAll()
}
