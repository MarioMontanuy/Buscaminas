package com.example.buscaminas.database

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class GameResultRepository(private val gameResultDao: GameResultDao) {

    var allGames: Flow<List<GameResult>> = gameResultDao.getAlphabetizedWords()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(gameResult: GameResult) {
        gameResultDao.insert(gameResult)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteEntry(id: Int) {
        gameResultDao.deleteEntry(id)
    }

}
