package com.fgiannesini.domain

class PlayerService(
    private val playerPersistence: PlayerPersistence,
    private val playerIdGenerator: PlayerIdGenerator
) {

    fun get(playerId: String): Player {
        return playerPersistence.findBy(playerId)
    }

    fun create(pseudo: String): Player {
        val playerWithId = Player(playerIdGenerator.new(), pseudo, 0)
        playerPersistence.save(playerWithId)
        return playerWithId
    }

    fun update(points: Int): Player {
        return NOT_FOUND
    }
}