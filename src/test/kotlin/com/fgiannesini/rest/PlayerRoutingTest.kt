package com.fgiannesini.rest

import com.fgiannesini.domain.Player
import com.fgiannesini.domain.PlayerService
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.testing.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Assertions.assertEquals
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.skyscreamer.jsonassert.JSONAssert.assertEquals
import org.skyscreamer.jsonassert.JSONCompareMode
import kotlin.test.Test

class PlayerRoutingTest {

    private fun Application.testModule(playerService: PlayerService) {
        install(Koin) {
            modules(module {
                single<PlayerService> { playerService }
            })
        }
        playerRouting()
    }

    @Test
    fun `Should get a player`() = testApplication {
        val playerService = mockk<PlayerService>()
        every { playerService.get("1") } returns Player("1", "aRandomPseudo", 5)
        application { testModule(playerService) }

        val response = client.get("/player/1")

        assertEquals(HttpStatusCode.OK, response.status)
        @Language("JSON")
        val expected = """{
            "id": "1",
            "pseudo": "aRandomPseudo",
            "points": 5
            }"""
        assertEquals(expected, response.bodyAsText(), JSONCompareMode.STRICT)
        verify(exactly = 1) { playerService.get(any()) }
    }

    @Test
    fun `Should create a player`() = testApplication {
        val playerService = mockk<PlayerService>()
        every { playerService.create("aRandomPseudo") } returns Player("1", "aRandomPseudo", 0)
        application { testModule(playerService) }

        @Language("JSON")
        val body = """{
            "pseudo": "aRandomPseudo"
            }"""
        val response = client.post("/player") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }

        assertEquals(HttpStatusCode.Created, response.status)
        assertEquals("/player/1", response.headers.get(HttpHeaders.Location))
        verify(exactly = 1) { playerService.create(any()) }
    }

    @Test
    fun `Should update a player`() = testApplication {
        val playerService = mockk<PlayerService>()
        every { playerService.update("1", 10) } returns Player("1", "aRandomPseudo", 10)
        application { testModule(playerService) }

        @Language("JSON")
        val body = """{
              "points": 10
            }"""
        val response = client.patch("/player/1") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }
        assertEquals(HttpStatusCode.NoContent, response.status)
        verify(exactly = 1) { playerService.update(any(), any()) }
    }
}