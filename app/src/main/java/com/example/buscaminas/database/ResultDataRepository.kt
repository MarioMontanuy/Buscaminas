package com.example.buscaminas.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class ResultDataRepository(private val resultDataDao: ResultDataDao) {

    val allWords: List<ResultDataEntity> = resultDataDao.getAllEntries()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: ResultDataEntity) {
        resultDataDao.insert(word)
    }

    /*@Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend  fun getAllResultFromDatabase(): List<ResultDataEntity>{
        return allWords
    }*/

    /*@Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend  fun getListDataFromDatabase(): List<ResultDataEntity>{
        return resultDataDao.getPlayerNameEntries()
    }*/

}
