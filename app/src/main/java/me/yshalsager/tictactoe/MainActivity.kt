package me.yshalsager.tictactoe

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import me.yshalsager.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var selectedPlayer: Int? = null
    private var playBoard: Array<Array<Int>> =
        arrayOf(arrayOf(0, 0, 0), arrayOf(0, 0, 0), arrayOf(0, 0, 0))
    private var gameEnded: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        val selectedId = binding.playerOptions.checkedRadioButtonId
        selectedPlayer = when (selectedId) {
            R.id.player_1 -> 1
            else -> 2
        }

        binding.player1.setOnClickListener { selectedPlayer = 1 }
        binding.player2.setOnClickListener { selectedPlayer = 2 }
    }

    fun play(view: View) {
        if (gameEnded) {
            return
        }
        if (selectedPlayer == 1) {
            view.setBackgroundResource(R.drawable.x)
        } else {
            view.setBackgroundResource(R.drawable.o)
        }
        view.isClickable = false
        val coordinators: List<String> =
            resources.getResourceName(view.id).split('/')[1].split('_').slice(2..3)
        val yCoordinate: Int = coordinators[1].toInt()
        val xCoordinate: Int = coordinators[0].toInt()
        playBoard[yCoordinate][xCoordinate] = selectedPlayer!!
        val (hasWon, winner) = win()
        if (hasWon) {
            binding.winnerPlayer.text =
                getString(
                    R.string.player_s_is_the_winner,
                    winner.toString()
                )
            binding.winnerPlayer.visibility = View.VISIBLE
            binding.playAgainBtn.visibility = View.VISIBLE
            gameEnded = true
        }
    }

    private fun arrayItemsAreSame(array: Array<Int>): Boolean {
        return array.count { it == array[0] } == array.count() && array[0] != 0
    }

    private fun win(): Pair<Boolean, Int?> {
        // horizontal win
        for (row in playBoard) {
            if (arrayItemsAreSame(row)) {
                return Pair(true, row[0])
            }
        }
        // vertical win
        for (column in 0 until playBoard[0].count()) {
            val toCheck = mutableListOf<Int>()
            for (row in playBoard) {
                toCheck.add(row[column])
            }
            if (arrayItemsAreSame(toCheck.toTypedArray())) {
                return Pair(true, toCheck[0])
            }
        }
        // diagonal win \
        val leftDiagonal = mutableListOf<Int>()
        for (idx in 0 until playBoard.count()) {
            leftDiagonal.add(playBoard[idx][idx])
        }
        if (arrayItemsAreSame(leftDiagonal.toTypedArray())) {
            return Pair(true, leftDiagonal[0])
        }
        // diagonal win /
        val rightDiagonal = mutableListOf<Int>()
        for ((idx, reverse_idx) in (0 until playBoard.count()).reversed().withIndex()) {
            rightDiagonal.add(playBoard[idx][reverse_idx])
        }
        if (arrayItemsAreSame(rightDiagonal.toTypedArray())) {
            return Pair(true, rightDiagonal[0])
        }
        // no win
        return Pair(false, null)
    }

    @Suppress("UNUSED_PARAMETER")
    fun restart(view: View) {
        this.recreate()
        binding.player1.isChecked = true
    }

}