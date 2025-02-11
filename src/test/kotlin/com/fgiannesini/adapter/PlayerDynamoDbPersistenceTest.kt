package com.fgiannesini.adapter


import com.fgiannesini.domain.NOT_FOUND
import com.fgiannesini.domain.Player
import com.fgiannesini.domain.PlayerPersistence
import com.fgiannesini.domain.dynamoDbModule
import org.junit.BeforeClass
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import kotlin.test.Test
import kotlin.test.assertEquals

class PlayerDynamoDbPersistenceTest {

    companion object {
        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            startKoin {
                modules(dynamoDbModule)
            }
        }
    }

    @Test
    fun `Should not find non-existing player`() {
        val playerPersistence: PlayerPersistence = getKoin().get()
        val retrievedUser = playerPersistence.findBy("not existing hash")
        assertEquals(NOT_FOUND, retrievedUser)
    }

    @Test
    fun `Should save and find a player`() {
        val playerPersistence: PlayerPersistence = getKoin().get()
        val id = "8c356f87-77cd-4483-89ff-1fc9dfbcc994"
        playerPersistence.save(
            Player(id, "aRandomPseudo", 50)
        )
        val player = playerPersistence.findBy(id)
        assertEquals(Player(id, "aRandomPseudo", 50), player)
    }


    @Test
    fun `Should find all players`() {
        val playerPersistence: PlayerPersistence = getKoin().get()
        val players = listOf(Player("1", "aRandomPseudo", 5), Player("2", "anOtherRandomPseudo", 10))
        players.forEach { playerPersistence.save(it) }

        val actualPlayers = playerPersistence.findAll()

        assertEquals(players, actualPlayers)
    }

    @Test
    fun `Should delete all players`() {
        val playerPersistence: PlayerPersistence = getKoin().get()
        playerPersistence.save(
            Player("1", "aRandomPseudo", 5)
        )
        playerPersistence.save(
            Player("2", "anOtherRandomPseudo", 10)
        )

        playerPersistence.deleteAll()

        assertEquals(NOT_FOUND, playerPersistence.findBy("1"))
        assertEquals(NOT_FOUND, playerPersistence.findBy("2"))
    }
}