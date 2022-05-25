package com.example.buscaminas.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [ResultDataEntity::class], version = 1, exportSchema = false)
abstract class ResultDataDatabase : RoomDatabase() {

    abstract fun resultDataDao(): ResultDataDao

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var resultDao = database.resultDataDao()

                    resultDao.deleteAll()

                    var result = ResultDataEntity("Mario"
/*, "CurrentDate", "5x5", "15%", "6", "120", "0", "Victoria"*/
)
                    resultDao.insert(result)
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ResultDataDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): ResultDataDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ResultDataDatabase::class.java,
                    "word_database"
                )
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
