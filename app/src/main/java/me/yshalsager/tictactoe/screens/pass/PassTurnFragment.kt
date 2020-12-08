package me.yshalsager.tictactoe.screens.pass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import me.yshalsager.tictactoe.R
import me.yshalsager.tictactoe.databinding.FragmentPassTurnBinding


class PassTurnFragment : Fragment() {
    private lateinit var binding: FragmentPassTurnBinding
    private var otherPlayer: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_pass_turn, container, false
        )
        val args = PassTurnFragmentArgs.fromBundle(arguments!!)

        otherPlayer = when (args.player) {
            0 -> 1
            1 -> 2
            else -> 1
        }

        binding.passBtn.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(
                    PassTurnFragmentDirections.actionPassTurnFragmentToGameFragment(
                        otherPlayer,
                        args.board,
                        0
                    )
                )
        }


        binding.playerTurn.text =
            getString(
                R.string.pass_device_to_player_s,
                otherPlayer.toString()
            )
        return binding.root
    }

}