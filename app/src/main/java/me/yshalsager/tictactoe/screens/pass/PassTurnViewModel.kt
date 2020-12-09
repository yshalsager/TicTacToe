package me.yshalsager.tictactoe.screens.pass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PassTurnViewModel(lastPlayer: Int) : ViewModel() {
    private val _lastPlayer = MutableLiveData<Int>()

    private val _nextPlayer = MutableLiveData<Int>()
    val nextPlayer: LiveData<Int>
        get() = _nextPlayer

    private val _eventPassTurn = MutableLiveData<Boolean>()
    val eventPassTurn: LiveData<Boolean>
        get() = _eventPassTurn


    init {
        _lastPlayer.value = lastPlayer
        _eventPassTurn.value = false
        swapPlayer()
    }

    private fun swapPlayer() {
        _nextPlayer.value = when (_lastPlayer.value) {
            0 -> 1
            1 -> 2
            else -> 1
        }
    }


    fun pass() {
        _eventPassTurn.value = true
    }


    fun onPassTurnComplete() {
        _eventPassTurn.value = false
    }
}