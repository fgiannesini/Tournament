package com.fgiannesini.domain

class PlayersService(
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

    fun update(playerId: String, score: Int): Player {
        val player = get(playerId)
        if (player == NOT_FOUND) {
            return NOT_FOUND
        }
        val updatedPlayer = player.copy(score = score)
        playerPersistence.save(updatedPlayer)
        return updatedPlayer
    }

    fun deleteAll() {
        playerPersistence.deleteAll()
    }

    fun findAll(): List<Player> {
        return playerPersistence.findAll()
    }
}