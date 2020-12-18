package me.yshalsager.tictactoe.screens.stats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.yshalsager.tictactoe.database.Game
import me.yshalsager.tictactoe.databinding.ListItemViewBinding

class ViewHolder private constructor(val binding: ListItemViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Game) {
        binding.game = item
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemViewBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding)
        }
    }
}