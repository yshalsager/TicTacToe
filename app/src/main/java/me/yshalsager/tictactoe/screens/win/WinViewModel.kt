package me.yshalsager.tictactoe.screens.win

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WinViewModel(winnerPlayer: Int) : ViewModel() {
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
    }

    fun playAgain() {
        _eventPlayAgain.value = true
    }

    fun onPlayAgainComplete() {
        _eventPlayAgain.value = false
    }

}