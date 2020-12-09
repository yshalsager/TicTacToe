package me.yshalsager.tictactoe.screens.win

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WinViewModelFactory(private val winnerPlayer: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WinViewModel::class.java)) {
            return WinViewModel(winnerPlayer) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}