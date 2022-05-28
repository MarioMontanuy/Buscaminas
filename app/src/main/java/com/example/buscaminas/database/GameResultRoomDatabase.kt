package com.example.buscaminas.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(GameResult::class), version = 1, exportSchema = false)
abstract class GameResultRoomDatabase : RoomDatabase() {

    abstract fun wordDao(): GameResultDao

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val wordDao = database.wordDao()

                    // Delete all content here.
                    wordDao.deleteAll()

                    /*// Add sample words.
                    var word = GameResult("Hello")
                    wordDao.insert(word)*/
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: GameResultRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): GameResultRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameResultRoomDatabase::class.java,
                    "word_database"
                )
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}

