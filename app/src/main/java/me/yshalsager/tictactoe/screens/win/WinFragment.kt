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
import androidx.navigation.findNavController
import me.yshalsager.tictactoe.R
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
        binding.playAgainBtn.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(WinFragmentDirections.actionWinFragmentToHomeFragment())
        }
        val args = WinFragmentArgs.fromBundle(arguments!!)
        binding.winnerPlayer.text =
            getString(
                R.string.player_s_is_the_winner,
                args.winner.toString()
            )

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