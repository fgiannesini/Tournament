package com.fgiannesini.domain

class PlayerService(playerPersistence: PlayerPersistence) {
    fun get(playerId: String): Player {
        return Player(playerId, "aRandomPseudo")
    }
    fun create(player :Player) : Player {
        return player.copy(id = "1")
    }
}