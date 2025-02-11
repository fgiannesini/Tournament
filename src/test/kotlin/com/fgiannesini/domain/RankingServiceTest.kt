package com.fgiannesini.domain

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertEquals

class RankingServiceTest {

    @Test
    fun `Should get a ranking`() {
        val playersService = mockk<PlayersService>()
        every { playersService.findAll() } returns listOf(
            Player("1", "second", 5),
            Player("2", "first", 10)
        )
        val rankingService = RankingService(playersService)
        val rank = rankingService.get(Player("1", "second", 5))

        assertEquals(Ranking(2), rank)
        verify(exactly = 1) { playersService.findAll() }
    }

    @Test
    fun `Should get same ranking with equal score`() {
        val playersService = mockk<PlayersService>()
        every { playersService.findAll() } returns listOf(
            Player("1", "first", 10),
            Player("2", "first", 10)
        )
        val rankingService = RankingService(playersService)

        assertEquals(Ranking(1), rankingService.get(Player("1", "first", 10)))
        assertEquals(Ranking(1), rankingService.get(Player("2", "first", 10)))
    }
}