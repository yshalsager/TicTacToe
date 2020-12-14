package me.yshalsager.tictactoe.screens.win

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.yshalsager.tictactoe.database.Game
import me.yshalsager.tictactoe.database.GameDatabaseDao

class WinViewModel(winnerPlayer: Int, val database: GameDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    private var lastGame = MutableLiveData<Game?>()

    private var _winnerPlayer = MutableLiveData<Int>()
    val winnerPlayer: LiveData<Int>
        get() = _winnerPlayer

    private val _eventPlayAgain = MutableLiveData<Boolean>()
    val eventPlayAgain: LiveData<Boolean>
        get() = _eventPlayAgain

    private val _isDraw = MutableLiveData<Boolean>()
    val isDraw: LiveData<Boolean>
        get() = _isDraw


    init {
        _winnerPlayer.value = winnerPlayer
        _eventPlayAgain.value = false
        _isDraw.value = winnerPlayer == 0
        viewModelScope.launch {
            val game = getLastGameFromDatabase()
            if (game != null) {
                game.winnerId = winnerPlayer
                game.endTimeMilli = System.currentTimeMillis()
                updateGame(game)
                lastGame.value = game
            }
        }
    }

    private suspend fun updateGame(game: Game) {
        database.update(game)
    }

    private suspend fun getLastGameFromDatabase(): Game? {
        var game = database.getLastGame()
        // check if the game has been ended already which shouldn't happen
        if (game?.endTimeMilli != game?.startTimeMilli) {
            game = null
        }
        return game
    }

    fun playAgain() {
        _eventPlayAgain.value = true
    }

    fun onPlayAgainComplete() {
        _eventPlayAgain.value = false
    }

}