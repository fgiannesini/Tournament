package com.fgiannesini.domain

class RankingService(private val playersService: PlayersService) {

    fun get(player: Player): Ranking {
        return playersService.findAll()
            .sortedByDescending { it.score }
            .indexOfFirst { it.score == player.score }
            .let { Ranking(it + 1) }
    }
}