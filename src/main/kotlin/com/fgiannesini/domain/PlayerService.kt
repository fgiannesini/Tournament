package com.fgiannesini.domain

class PlayerService(
    private val playerPersistence: PlayerPersistence,
    private val playerIdGenerator: PlayerIdGenerator
) {

    fun get(playerId: String): Player {
        return Player(playerId, "aRandomPseudo")
    }

    fun create(player :Player) : Player {
        val playerWithId = player.copy(id = playerIdGenerator.new())
        playerPersistence.save(playerWithId)
        return playerWithId
    }
}