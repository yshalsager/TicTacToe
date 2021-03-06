package me.yshalsager.tictactoe.screens.win

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.yshalsager.tictactoe.database.GameDatabaseDao

class WinViewModelFactory(
    private val winnerPlayer: Int,
    private val dataSource: GameDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WinViewModel::class.java)) {
            return WinViewModel(winnerPlayer, dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}