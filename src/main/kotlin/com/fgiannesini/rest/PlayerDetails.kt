package com.fgiannesini.rest

import com.fgiannesini.domain.Player
import com.fgiannesini.domain.Ranking
import kotlinx.serialization.Serializable

@Serializable
data class PlayerDetails(val id: String, val pseudo: String, val score: Int, val rank: Int) {
    companion object {
        fun from(player: Player, ranking: Ranking): PlayerDetails {
            return PlayerDetails(player.id, player.pseudo, player.score, ranking.value)
        }
    }
}
