package me.yshalsager.tictactoe.screens.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.yshalsager.tictactoe.database.Game
import me.yshalsager.tictactoe.database.GameDatabaseDao

class HomeViewModel(private val database: GameDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    private val _eventGameStart = MutableLiveData<Boolean>()
    val eventGameStart: LiveData<Boolean>
        get() = _eventGameStart

    init {
        _eventGameStart.value = false
    }


    fun onGameStart() {
        _eventGameStart.value = true
        onStartPlaying()
    }

    fun onGameStartComplete() {
        _eventGameStart.value = false
    }

    private suspend fun insert(game: Game) {
        database.insert(game)
    }


    private fun onStartPlaying() {
        viewModelScope.launch {
            // Create a new game, which captures the current time,
            // and insert it into the database.
            val newGame = Game()

            insert(newGame)
        }
    }

}