package com.fgiannesini.domain

class RankingService(private val playersService: PlayersService) {

    fun get(player: Player): Ranking {
        return playersService.findAll()
            .sortedByDescending { it.score }
            .indexOfFirst { it == player }
            .let { Ranking(it + 1) }
    }
}