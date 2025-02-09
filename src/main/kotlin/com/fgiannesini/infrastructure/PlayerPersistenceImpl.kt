package com.fgiannesini.infrastructure

import com.fgiannesini.domain.Player
import com.fgiannesini.domain.PlayerPersistence

class PlayerPersistenceImpl : PlayerPersistence {
    override fun findBy(playerId: Long): Player {
        return Player(playerId, "toto")
    }
}