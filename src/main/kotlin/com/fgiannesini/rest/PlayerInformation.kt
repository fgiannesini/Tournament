package com.fgiannesini.rest

import com.fgiannesini.domain.Player
import kotlinx.serialization.Serializable

@Serializable
data class PlayerInformation(val id: String, val pseudo: String, val score: Int) {
    companion object {
        fun from(player : Player) : PlayerInformation {
            return PlayerInformation(player.id, player.pseudo, player.score)
        }
    }
}
