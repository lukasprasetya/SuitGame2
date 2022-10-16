package com.lupa.suitgame2.usecase

import com.lupa.suitgame2.enum.PlayerChar
import com.lupa.suitgame2.ui.main.WinnerPlayer

class GetWinnerUseCase : WinnerPlayer {

    companion object {
        const val DRAW = 0
        const val PLAYER_ONE_WINNER = 1
        const val PLAYER_TWO_WINNER = 2
    }

    override fun onWinnerPlayer(playerOne: Int, playerTwo: Int): Int {
        return if (playerOne == playerTwo) {
            DRAW
        } else if (playerOne == PlayerChar.ROCK.ordinal) {
            if (playerTwo == PlayerChar.SCISSOR.ordinal) {
                PLAYER_ONE_WINNER
            } else {
                PLAYER_TWO_WINNER
            }
        } else if (playerOne == PlayerChar.PAPER.ordinal) {
            if (playerTwo == PlayerChar.ROCK.ordinal) {
                PLAYER_ONE_WINNER
            } else {
                PLAYER_TWO_WINNER
            }
        } else {
            if (playerTwo == PlayerChar.PAPER.ordinal) {
                PLAYER_ONE_WINNER
            } else {
                PLAYER_TWO_WINNER
            }
        }
    }


}