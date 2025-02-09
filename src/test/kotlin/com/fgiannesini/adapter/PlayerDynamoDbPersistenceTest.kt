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
            Player(id, "aRandomPseudo")
        )
        val player = playerPersistence.findBy(id)
        assertEquals(Player(id, "aRandomPseudo"), player)
    }
}