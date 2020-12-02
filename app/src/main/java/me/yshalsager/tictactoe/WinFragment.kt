package me.yshalsager.tictactoe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import me.yshalsager.tictactoe.databinding.FragmentWinBinding


class WinFragment : Fragment() {
    private lateinit var binding: FragmentWinBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_win, container, false
        )
        binding.playAgainBtn.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_winFragment_to_homeFragment)
        )

        return binding.root
    }

}