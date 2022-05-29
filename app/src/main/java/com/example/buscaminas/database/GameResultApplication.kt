package com.example.buscaminas.database

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class GameResultApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { GameResultRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { GameResultRepository(database.wordDao()) }
}
