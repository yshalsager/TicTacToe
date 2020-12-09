package me.yshalsager.tictactoe.screens.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.navGraphViewModels
import me.yshalsager.tictactoe.R
import me.yshalsager.tictactoe.databinding.FragmentGameBinding


class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding
    private val viewModel: GameViewModel by navGraphViewModels(R.id.navigation)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_game, container, false
        )
        binding.lifecycleOwner = this
        binding.gameViewModel = viewModel

        viewModel.eventGameFinish.observe(viewLifecycleOwner, { isFinished ->
            if (isFinished) {
                viewModel.onGameFinishComplete()
                findNavController(this).navigate(
                    GameFragmentDirections.actionGameFragmentToWinFragment()
                )
            }
        })

        viewModel.eventTurnFinish.observe(viewLifecycleOwner, { isFinished ->
            if (!isFinished) {
                viewModel.onTurnComplete()
                binding.invalidateAll()
                val hasWon = viewModel.win()
                if (!hasWon) {
                    findNavController(this).navigate(
                        GameFragmentDirections.actionGameFragmentToPassTurnFragment()
                    )
                }
            }
        })

        return binding.root
    }

}