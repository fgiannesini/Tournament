package com.fgiannesini.domain

class RankingService(private val playerService: PlayerService) {

    fun get(player: Player): Ranking {
        return playerService.findAll()
            .sortedByDescending { it.score }
            .indexOfFirst { it == player }
            .let { Ranking(it + 1) }
    }
}