package me.yshalsager.tictactoe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import me.yshalsager.tictactoe.databinding.FragmentGameBinding


class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding
    private var selectedPlayer: Int? = null
    private var playBoard: Array<Array<Int>> =
        arrayOf(arrayOf(0, 0, 0), arrayOf(0, 0, 0), arrayOf(0, 0, 0))
    private var gameEnded: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_game, container, false
        )
        val selectedId = binding.playerOptions.checkedRadioButtonId
        selectedPlayer = when (selectedId) {
            R.id.player_1 -> 1
            else -> 2
        }

        binding.player1.setOnClickListener { selectedPlayer = 1 }
        binding.player2.setOnClickListener { selectedPlayer = 2 }
        setListeners()


        return binding.root
    }

    private fun setListeners() {
        val clickableViews: List<ImageView> =
            listOf(
                binding.boardPlace00,
                binding.boardPlace01,
                binding.boardPlace02,
                binding.boardPlace10,
                binding.boardPlace11,
                binding.boardPlace12,
                binding.boardPlace20,
                binding.boardPlace21,
                binding.boardPlace22
            )

        for (item in clickableViews) {
            item.setOnClickListener { play(it) }
        }
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

        // TODO: Navigate to win fragment when there's a winner and say who won
        val (hasWon, winner) = win()
        if (hasWon) {
            view.findNavController().navigate(R.id.action_gameFragment_to_winFragment)
//            binding.winnerPlayer.text =
//                getString(
//                    R.string.player_s_is_the_winner,
//                    winner.toString()
//                )
//            binding.winnerPlayer.visibility = View.VISIBLE
//            binding.playAgainBtn.visibility = View.VISIBLE
//            gameEnded = true
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
}