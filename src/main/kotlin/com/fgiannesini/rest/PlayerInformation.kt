package com.fgiannesini.rest

import com.fgiannesini.domain.Player
import kotlinx.serialization.Serializable

@Serializable
data class PlayerInformation(val id: Long, val pseudo: String) {
    companion object {
        fun from(player : Player) : PlayerInformation {
            return PlayerInformation(player.id!!, player.pseudo)
        }
    }
}
