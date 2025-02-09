package com.fgiannesini.domain

import io.mockk.*
import kotlin.test.Test
import kotlin.test.assertEquals

class PlayerServiceTest {

    @Test
    fun `Should get a player`() {
        val playerPersistence = mockk<PlayerPersistence>()
        every { playerPersistence.findBy("1") } returns Player("1", "aRandomPseudo", 0)

        val playerService = PlayerService(playerPersistence, mockk<PlayerIdGenerator>())
        val player = playerService.get("1")

        assertEquals(Player("1", "aRandomPseudo", 0), player)
        verify(exactly = 1) { playerPersistence.findBy(any()) }
    }

    @Test
    fun `Should save a player`() {
        val playerPersistence = mockk<PlayerPersistence>()
        every { playerPersistence.save(any()) } just runs

        val playerIdGenerator = mockk<PlayerIdGenerator>()
        every { playerIdGenerator.new() } returns "550e8400-e29b-41d4-a716-446655440000"

        val playerService = PlayerService(playerPersistence, playerIdGenerator)
        val created = playerService.create(Player(NO_ID, "aRandomPseudo", 0))

        val expected = Player("550e8400-e29b-41d4-a716-446655440000", "aRandomPseudo", 0)
        assertEquals(expected, created)
        verify(exactly = 1) { playerPersistence.save(expected) }
    }
}