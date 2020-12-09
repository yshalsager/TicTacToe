package me.yshalsager.tictactoe.screens.pass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.navArgs
import me.yshalsager.tictactoe.R
import me.yshalsager.tictactoe.databinding.FragmentPassTurnBinding


class PassTurnFragment : Fragment() {
    private lateinit var binding: FragmentPassTurnBinding
    private lateinit var viewModel: PassTurnViewModel
    private lateinit var viewModelFactory: PassTurnViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_pass_turn, container, false
        )

        val passFragmentArgs by navArgs<PassTurnFragmentArgs>()
        viewModelFactory = PassTurnViewModelFactory(passFragmentArgs.lastPlayer)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(PassTurnViewModel::class.java)

        binding.passTurnViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.eventPassTurn.observe(viewLifecycleOwner, { pass ->
            if (pass) {
                findNavController(this)
                    .navigate(
                        PassTurnFragmentDirections.actionPassTurnFragmentToGameFragment(
                            0
                        )
                    )
                viewModel.onPassTurnComplete()
            }
        })

        return binding.root
    }
}