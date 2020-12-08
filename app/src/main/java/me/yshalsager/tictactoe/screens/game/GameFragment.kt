package me.yshalsager.tictactoe.screens.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.navGraphViewModels
import me.yshalsager.tictactoe.R
import me.yshalsager.tictactoe.databinding.FragmentGameBinding


class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding
    private lateinit var clickableViews: List<Button>
    private val viewModel: GameViewModel by navGraphViewModels(R.id.navigation)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_game, container, false
        )
        binding.playerTurn.text =
            getString(
                R.string.it_s_player_s_turn,
                viewModel.selectedPlayer.value.toString()
            )

        setListeners()
        restoreBoard()

        viewModel.eventGameFinish.observe(viewLifecycleOwner, { isFinished ->
            if (isFinished) {
                viewModel.onGameFinishComplete()
                gameFinished()
            }
        })

        return binding.root
    }

    private fun gameFinished() {
        findNavController(this).navigate(
            GameFragmentDirections.actionGameFragmentToWinFragment()
        )
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
        // TODO separate this part of logic correctly
        val toPlay: String = if (viewModel.selectedPlayer.value == 1) "X" else "O"
        button.text = toPlay
        viewModel.play(resources.getResourceName(button.id).takeLast(1).toInt(), toPlay)
        viewModel.swapPlayer()
        button.isClickable = false
        val hasWon = viewModel.win()
        if (!hasWon) {
            button.findNavController().navigate(
                GameFragmentDirections.actionGameFragmentToPassTurnFragment()
            )
        }
    }

    private fun restoreBoard() {
        viewModel.playBoard.value?.zip(clickableViews)?.forEach { pair ->
            pair.component2().text = pair.component1()
        }
    }

}