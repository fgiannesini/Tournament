package com.fgiannesini.domain

interface PlayerPersistence {

    fun findBy(playerId: String): Player
    fun save(player: Player)
    fun deleteAll()
    fun findAll(): List<Player>
}