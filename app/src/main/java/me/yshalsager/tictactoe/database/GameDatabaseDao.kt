package me.yshalsager.tictactoe.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface GameDatabaseDao {
    @Insert
    suspend fun insert(game: Game)

    @Update
    suspend fun update(game: Game)

    @Query("SELECT * from games_table WHERE gameId = :key")
    suspend fun get(key: Long): Game?

    @Query("DELETE FROM games_table")
    suspend fun clear()

    @Query("SELECT * FROM games_table ORDER BY gameId DESC")
    fun getAllGames(): LiveData<List<Game>>

    @Query("SELECT COUNT(*) from games_table")
    fun getTotalGamesCount(): LiveData<Int>

    @Query("SELECT COUNT(*) from games_table WHERE game_winner = 1")
    fun getXWinsCount(): LiveData<Int>

    @Query("SELECT COUNT(*) from games_table WHERE game_winner = 2")
    fun getYWinsCount(): LiveData<Int>

    @Query("SELECT COUNT(*) from games_table WHERE game_winner = 0")
    fun getDrawCount(): LiveData<Int>

    @Query("SELECT * FROM games_table ORDER BY gameId DESC LIMIT 1")
    suspend fun getLastGame(): Game?
}