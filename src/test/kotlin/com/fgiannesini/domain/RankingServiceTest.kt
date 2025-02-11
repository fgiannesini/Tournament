package com.fgiannesini.domain

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertEquals

class RankingServiceTest {

    @Test
    fun `Should get a ranking`() {
        val playerService = mockk<PlayerService>()
        every { playerService.findAll() } returns listOf(
            Player("1", "second", 5),
            Player("2", "first", 10)
        )
        val rankingService = RankingService(playerService)
        val rank = rankingService.get(Player("1", "second", 5))

        assertEquals(Ranking(2), rank)
        verify(exactly = 1) { playerService.findAll() }
    }
}