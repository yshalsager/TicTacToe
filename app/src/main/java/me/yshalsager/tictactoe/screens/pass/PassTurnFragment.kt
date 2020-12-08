package me.yshalsager.tictactoe.screens.pass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import me.yshalsager.tictactoe.R
import me.yshalsager.tictactoe.databinding.FragmentPassTurnBinding
import me.yshalsager.tictactoe.screens.game.GameViewModel


class PassTurnFragment : Fragment() {
    private lateinit var binding: FragmentPassTurnBinding
    private val viewModel: GameViewModel by navGraphViewModels(R.id.navigation)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_pass_turn, container, false
        )

        binding.passBtn.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(
                    PassTurnFragmentDirections.actionPassTurnFragmentToGameFragment()
                )
        }

        binding.playerTurn.text =
            getString(
                R.string.pass_device_to_player_s,
                viewModel.selectedPlayer.value.toString()
            )
        return binding.root
    }
}