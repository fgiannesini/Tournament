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
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Assertions.assertEquals
import org.koin.ktor.plugin.Koin
import org.skyscreamer.jsonassert.JSONAssert.assertEquals
import org.skyscreamer.jsonassert.JSONCompareMode
import kotlin.test.Test

class PlayerRoutingTest {

    private fun Application.testModule(playerService: PlayerService) {
        install(Koin) {
            modules(org.koin.dsl.module {
                single<PlayerService> { playerService }
            })
        }
        playerRouting()
    }

    @Test
    fun `Should get a player`() = testApplication {
        val playerService = mockk<PlayerService>()
        every { playerService.get("1") } returns Player("1", "aRandomPseudo")
        application { testModule(playerService) }

        val response = client.get("/player/1")

        assertEquals(HttpStatusCode.OK, response.status)
        @Language("JSON")
        val expected = """{
            "id": "1",
            "pseudo": "aRandomPseudo"
            }"""
        assertEquals(expected, response.bodyAsText(), JSONCompareMode.STRICT)
    }

    @Test
    fun `Should create a player`() = testApplication {
        val playerService = mockk<PlayerService>()
        every { playerService.create(any()) } returns Player("1", "aRandomPseudo")
        application { testModule(playerService) }

        @Language("JSON")
        val body = """{
            "pseudo": "aRandomPseudo"
            }"""
        val response = client.post("/player") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        @Language("JSON")
        val expected = """{
            "id": "1",
            "pseudo": "aRandomPseudo"
            }"""
        assertEquals(expected, response.bodyAsText(), JSONCompareMode.STRICT)
    }
}