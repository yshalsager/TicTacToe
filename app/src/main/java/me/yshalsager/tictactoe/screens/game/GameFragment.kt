package me.yshalsager.tictactoe.screens.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import me.yshalsager.tictactoe.R
import me.yshalsager.tictactoe.databinding.FragmentGameBinding


/** onSaveInstanceState Bundle Keys **/
const val KEY_PLAYER = "selected_player_key"
const val KEY_BOARD = "play_board_key"
const val KEY_GAME_ENDED = "game_ended_key"


class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding
    private var selectedPlayer: Int = 1
    private var playBoard = arrayListOf("", "", "", "", "", "", "", "", "")
    private var gameEnded: Boolean = false
    private lateinit var clickableViews: List<Button>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_game, container, false
        )
        val args = GameFragmentArgs.fromBundle(arguments!!)
        if (args.board.isNotEmpty()) {
            playBoard = args.board.toList() as ArrayList<String>
        }

        selectedPlayer = args.player
        binding.playerTurn.text =
            getString(
                R.string.it_s_player_s_turn,
                selectedPlayer.toString()
            )

        setListeners()
        restoreBoard()

        if (savedInstanceState != null) {
            // Get all the game state information from the bundle, set it
            selectedPlayer = savedInstanceState.getInt(KEY_PLAYER)
            playBoard = savedInstanceState.getSerializable(KEY_BOARD) as ArrayList<String>
            gameEnded = savedInstanceState.getBoolean(KEY_GAME_ENDED)
            restoreBoard()
        }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_PLAYER, selectedPlayer)
        outState.putSerializable(KEY_BOARD, playBoard)
        outState.putBoolean(KEY_GAME_ENDED, gameEnded)
    }

    private fun setListeners() {
        clickableViews = listOf(
            binding.button0, binding.button1, binding.button2, binding.button3,
            binding.button4, binding.button5, binding.button6, binding.button7,
            binding.button8
        )

        clickableViews.forEach { button: Button ->
            button.setOnClickListener { play(button) }
        }
    }

    private fun play(button: Button) {
        if (gameEnded) {
            return
        }
        playTurn(button, selectedPlayer)
        button.isClickable = false
        val (hasWon, winner) = win()
        if (hasWon) {
            button.findNavController()
                .navigate(GameFragmentDirections.actionGameFragmentToWinFragment(winner!!))
        } else {
            button.findNavController()
                .navigate(
                    GameFragmentDirections.actionGameFragmentToPassTurnFragment(
                        selectedPlayer,
                        playBoard.toTypedArray()
                    )
                )
        }
    }

    private fun playTurn(button: Button, player: Int) {
        val toPlay: String = if (player == 1) "X" else "O"
        button.text = toPlay
        playBoard[resources.getResourceName(button.id).takeLast(1).toInt()] = toPlay
    }

    private fun arrayItemsAreSame(array: List<String>): Boolean =
        array.count { it == array[0] } == array.count() && array[0] != ""

    private fun getPlayerNumberFromBoard(cell: String): Int = if (cell == "X") 1 else 2

    private fun win(): Pair<Boolean, Int?> {
        // split the board into 3 chunks to make win calculations
        val board: List<List<String>> = playBoard.chunked(3)

        // horizontal win ـ
        board.forEach {
            if (arrayItemsAreSame(it)) {
                return Pair(true, getPlayerNumberFromBoard(it[0]))
            }
        }
        // vertical win |
        for (column in 0 until board[0].count()) {
            val toCheck = mutableListOf<String>()
            for (row in board) {
                toCheck.add(row[column])
            }
            if (arrayItemsAreSame(toCheck)) {
                return Pair(true, getPlayerNumberFromBoard(toCheck[0]))
            }
        }
        // diagonal win \
        val leftDiagonal = (0 until board.count()).map { board[it][it] }
        if (arrayItemsAreSame(leftDiagonal)) {
            return Pair(true, getPlayerNumberFromBoard(leftDiagonal[0]))
        }
        // diagonal win /
        val rightDiagonal = mutableListOf<String>()
        (0 until board.count()).reversed()
            .mapIndexedTo(rightDiagonal) { idx, reverse_idx -> board[idx][reverse_idx] }
        if (arrayItemsAreSame(rightDiagonal)) {
            return Pair(true, getPlayerNumberFromBoard(rightDiagonal[0]))
        }
        // no win
        return Pair(false, null)
    }

    private fun restoreBoard() {
        playBoard.zip(clickableViews).forEach { pair ->
            pair.component2().text = pair.component1()
        }
    }

}