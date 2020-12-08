package me.yshalsager.tictactoe.screens.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private val _selectedPlayer = MutableLiveData<Int>()
    val selectedPlayer: LiveData<Int>
        get() = _selectedPlayer

    private var _winnerPlayer = MutableLiveData<Int>()
    val winnerPlayer: LiveData<Int>
        get() = _winnerPlayer

    private var _playBoard = MutableLiveData<ArrayList<String>>()
    val playBoard: LiveData<ArrayList<String>>
        get() = _playBoard

    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish


    init {
        _selectedPlayer.value = 1
        _winnerPlayer.value = null
        _playBoard.value = arrayListOf("", "", "", "", "", "", "", "", "")
        _eventGameFinish.value = false
    }

    private fun arrayItemsAreSame(array: List<String>): Boolean =
        array.count { it == array[0] } == array.count() && array[0] != ""

    private fun getPlayerNumberFromBoard(cell: String): Int = if (cell == "X") 1 else 2

    fun swapPlayer() {
        _selectedPlayer.value = when (_selectedPlayer.value) {
            0 -> 1
            1 -> 2
            else -> 1
        }
    }

    fun win(): Boolean {
        // split the board into 3 chunks to make win calculations
        val board: List<List<String>>? = _playBoard.value?.chunked(3)

        // horizontal win ـ
        board?.forEach {
            if (arrayItemsAreSame(it)) {
                _winnerPlayer.value = getPlayerNumberFromBoard(it[0])
                _eventGameFinish.value = true
                return true
            }
        }
        // vertical win |
        for (column in 0 until board!![0].count()) {
            val toCheck = mutableListOf<String>()
            for (row in board) {
                toCheck.add(row[column])
            }
            if (arrayItemsAreSame(toCheck)) {
                _winnerPlayer.value = getPlayerNumberFromBoard(toCheck[0])
                _eventGameFinish.value = true
                return true
            }
        }
        // diagonal win \
        val leftDiagonal = (0 until board.count()).map { board[it][it] }
        if (arrayItemsAreSame(leftDiagonal)) {
            _winnerPlayer.value = getPlayerNumberFromBoard(leftDiagonal[0])
            _eventGameFinish.value = true
            return true
        }
        // diagonal win /
        val rightDiagonal = mutableListOf<String>()
        (0 until board.count()).reversed()
            .mapIndexedTo(rightDiagonal) { idx, reverse_idx -> board[idx][reverse_idx] }
        if (arrayItemsAreSame(rightDiagonal)) {
            _winnerPlayer.value = getPlayerNumberFromBoard(rightDiagonal[0])
            _eventGameFinish.value = true
            return true
        }
        // no win
        return false
    }

    override fun onCleared() {
        super.onCleared()
        _playBoard.value!!.forEachIndexed { index, _ ->
            _playBoard.value!![index] = ""
        }
        _selectedPlayer.value = 1
        _winnerPlayer.value = null
    }

    fun clear() = onCleared()

    fun play(place: Int, piece: String) {
        _playBoard.value?.set(place, piece)
    }

    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }
}