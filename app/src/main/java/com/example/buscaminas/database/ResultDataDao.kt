package com.example.buscaminas.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ResultDataDao {
    @Query("SELECT * FROM result_table ORDER BY playerName ASC")
    fun getAllEntries(): List<ResultDataEntity>

    /*@Query ("SELECT playerName FROM result_table")
    fun getPlayerNameEntries(): List<ResultDataEntity>*/

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: ResultDataEntity)

    @Query("DELETE FROM result_table")
    suspend fun deleteAll()
}
