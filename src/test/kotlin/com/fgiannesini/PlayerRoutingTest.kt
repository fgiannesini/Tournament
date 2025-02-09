package com.fgiannesini

import com.fgiannesini.domain.Player
import com.fgiannesini.domain.PlayerService
import com.fgiannesini.rest.playerRouting
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
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import kotlin.test.Test

class PlayerRoutingTest {

    @Test
    fun `Should return hello world when calling root`() = testApplication {
        application {
            module()
        }
        val response = client.get("/")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("Hello World!", response.bodyAsText())
    }

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
        every { playerService.get(1) } returns Player(1, "aRandomPseudo")
        application { testModule(playerService) }

        val response = client.get("/player/1")

        assertEquals(HttpStatusCode.OK, response.status)
        @Language("JSON")
        val expected = """{
            "id": 1,
            "pseudo": "aRandomPseudo"
            }"""
        JSONAssert.assertEquals(expected, response.bodyAsText(), JSONCompareMode.STRICT)
    }

    @Test
    fun `Should create a player`() = testApplication {
        val playerService = mockk<PlayerService>()
        every { playerService.create(any()) } returns Player(1, "aRandomPseudo")
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
            "id": 1,
            "pseudo": "aRandomPseudo"
            }"""
        JSONAssert.assertEquals(expected, response.bodyAsText(), JSONCompareMode.STRICT)
    }
}