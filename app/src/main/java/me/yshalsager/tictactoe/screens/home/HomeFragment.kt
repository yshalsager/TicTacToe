package me.yshalsager.tictactoe.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import me.yshalsager.tictactoe.R
import me.yshalsager.tictactoe.database.GameDatabase
import me.yshalsager.tictactoe.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = GameDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory = HomeViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        binding.homeViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.eventGameStart.observe(viewLifecycleOwner, { isStarted ->
            if (isStarted) {
                viewModel.onGameStartComplete()
                this.findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToPassTurnFragment(0))
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item, requireView().findNavController()
        )
                || super.onOptionsItemSelected(item)
    }

}