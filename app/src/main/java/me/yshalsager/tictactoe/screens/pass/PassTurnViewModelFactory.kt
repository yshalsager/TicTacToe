package me.yshalsager.tictactoe.screens.pass

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PassTurnViewModelFactory(private val lastPlayer: Int) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PassTurnViewModel::class.java)) {
            return PassTurnViewModel(lastPlayer) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}