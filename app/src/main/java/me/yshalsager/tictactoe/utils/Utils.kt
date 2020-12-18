package me.yshalsager.tictactoe.utils

import android.annotation.SuppressLint
import me.yshalsager.tictactoe.database.Game
import java.text.SimpleDateFormat

fun getGamesAsString(game: Game): String {
    val gamesString = StringBuilder()
    if (game.startTimeMilli != game.endTimeMilli) {
        gamesString.append("Game ${game.gameId}\n")
        gamesString.append("Started at: ${formatDateTime(game.startTimeMilli)}\n")
        gamesString.append("Ended at: ${formatDateTime(game.endTimeMilli)}\n")
        gamesString.append("Result: ${getWinnerFromID(game.winnerId)}")
    }
    return gamesString.toString()
}

fun getWinnerFromID(id: Int): String {
    return when (id) {
        1 -> "Player 1 Won"
        2 -> "Player 2 Won"
        else -> "Draw"
    }
}

@SuppressLint("SimpleDateFormat")
fun formatDateTime(datetime: Long): String {
    return SimpleDateFormat("EEEE dd-MM-yyyy HH:mm").format(datetime).toString()
}