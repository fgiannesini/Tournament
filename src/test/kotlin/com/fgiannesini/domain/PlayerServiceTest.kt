package com.fgiannesini.domain

import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals

class PlayerServiceTest {

    @Test
    fun `Should get a user`() {
        val playerPersistence = mockk<PlayerPersistence>()
        every { playerPersistence.findBy("1") } returns Player("1", "aRandomPseudo")

        val playerService = PlayerService(playerPersistence)
        val player = playerService.get("1")
        assertEquals(Player("1", "aRandomPseudo"), player)
    }
}