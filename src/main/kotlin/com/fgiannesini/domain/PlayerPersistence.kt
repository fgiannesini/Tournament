package com.fgiannesini.domain

interface PlayerPersistence {

    fun findBy(playerId: String): Player
    fun save(player: Player)
    fun deleteAll(limit: Int = 10000)
    fun findAll(limit: Int = 10000): List<Player>
}