package me.yshalsager.tictactoe.screens.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import me.yshalsager.tictactoe.R
import me.yshalsager.tictactoe.database.GameDatabase
import me.yshalsager.tictactoe.databinding.FragmentStatsBinding

class StatsFragment : Fragment() {
    private lateinit var binding: FragmentStatsBinding
    private lateinit var viewModel: StatsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_stats, container, false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = GameDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory = StatsViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(StatsViewModel::class.java)

        binding.statsViewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = GameAdapter()
        binding.gamesList.adapter = adapter

        viewModel.games.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.showSnackBarEvent.observe(viewLifecycleOwner, {
            if (it == true) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.cleared_message),
                    Snackbar.LENGTH_SHORT
                ).show()
                viewModel.doneShowingSnackbar()
            }
        })

        return binding.root
    }

}