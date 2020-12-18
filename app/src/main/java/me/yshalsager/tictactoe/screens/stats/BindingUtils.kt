package me.yshalsager.tictactoe.screens.stats

import android.widget.TextView
import androidx.databinding.BindingAdapter
import me.yshalsager.tictactoe.database.Game
import me.yshalsager.tictactoe.utils.getGamesAsString

@BindingAdapter("gameString")
fun TextView.setGameString(item: Game?) {
    item?.let {
        text = getGamesAsString(item)
    }
}
