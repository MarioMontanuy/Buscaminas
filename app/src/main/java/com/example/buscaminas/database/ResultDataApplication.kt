package com.example.buscaminas.database

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ResultDataApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { ResultDataDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { ResultDataRepository(database.resultDataDao()) }
}