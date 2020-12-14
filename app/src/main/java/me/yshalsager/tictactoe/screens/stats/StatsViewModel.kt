package me.yshalsager.tictactoe.screens.stats

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.yshalsager.tictactoe.database.Game
import me.yshalsager.tictactoe.database.GameDatabaseDao
import me.yshalsager.tictactoe.utils.getGamesAsString

class StatsViewModel(private val database: GameDatabaseDao, application: Application) :
    AndroidViewModel(application) {
    private val _games: LiveData<List<Game>> = database.getAllGames()
    val games = Transformations.map(_games) {
        _games.value?.let { gamesList -> getGamesAsString(gamesList) }
    }
    val xWins: LiveData<Int> = database.getXWinsCount()
    val yWins: LiveData<Int> = database.getYWinsCount()
    val drawCount: LiveData<Int> = database.getDrawCount()
    val totalGamesCount: LiveData<Int> = database.getTotalGamesCount()

    private var _showSnackbarEvent = MutableLiveData<Boolean>()
    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackbarEvent

    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = false
    }

    private suspend fun clear() = database.clear()

    fun onClear() {
        viewModelScope.launch {
            // Clear the database table.
            clear()
        }
        _showSnackbarEvent.value = true
    }

    /**
     * If there are any games in the database, show the CLEAR button.
     */
    val clearButtonVisible = Transformations.map(games) {
        it?.isNotEmpty()
    }

}