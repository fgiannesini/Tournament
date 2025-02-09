package com.fgiannesini.domain

interface PlayerPersistence {

    fun findBy(playerId: String): Player
}