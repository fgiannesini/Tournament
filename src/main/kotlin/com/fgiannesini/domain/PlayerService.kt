package com.fgiannesini.domain

class PlayerService {
    fun get(playerId: Long) : Player? {
        return null
    }
    fun create(player :Player) : Player {
        return player.copy(id = 1)
    }
}