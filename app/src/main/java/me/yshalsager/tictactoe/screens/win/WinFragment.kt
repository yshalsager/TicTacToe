package me.yshalsager.tictactoe.screens.win

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import me.yshalsager.tictactoe.R
import me.yshalsager.tictactoe.databinding.FragmentWinBinding


class WinFragment : Fragment() {
    private lateinit var binding: FragmentWinBinding
    private lateinit var viewModelFactory: WinViewModelFactory
    private lateinit var viewModel: WinViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_win, container, false
        )

        val winFragmentArgs by navArgs<WinFragmentArgs>()
        viewModelFactory = WinViewModelFactory(winFragmentArgs.winnerPlayer)
        viewModel = ViewModelProvider(this, viewModelFactory).get(WinViewModel::class.java)

        binding.winViewModel = viewModel
        binding.lifecycleOwner = this

        if (viewModel.isDraw.value == true) {
            binding.draw.visibility = View.VISIBLE
            binding.winnerPlayer.visibility = View.GONE
        }

        viewModel.eventPlayAgain.observe(viewLifecycleOwner, { play ->
            if (play) {
                NavHostFragment.findNavController(this)
                    .navigate(WinFragmentDirections.actionWinFragmentToHomeFragment())
                viewModel.onPlayAgainComplete()
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun getShareIntent(): Intent {
        return ShareCompat.IntentBuilder.from(requireActivity())
            .setText(getString(R.string.share_text)).setType("text/plain")
            .intent
    }

    private fun share() {
        try {
            startActivity(getShareIntent())
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                activity, getString(R.string.sharing_is_not_available),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.winner_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> share()
        }
        return super.onOptionsItemSelected(item)
    }

}