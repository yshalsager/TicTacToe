package me.yshalsager.tictactoe.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Game::class], version = 1, exportSchema = false)
abstract class GameDatabase : RoomDatabase() {

    /**
     * Connects the database to the DAO.
     */
    abstract val sleepDatabaseDao: GameDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: GameDatabase? = null

        fun getInstance(context: Context): GameDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GameDatabase::class.java,
                        "sleep_history_database"
                    )
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}