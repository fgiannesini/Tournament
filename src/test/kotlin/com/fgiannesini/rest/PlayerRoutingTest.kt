package com.fgiannesini.rest

import com.fgiannesini.domain.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.testing.*
import io.mockk.*
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Assertions.assertEquals
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.skyscreamer.jsonassert.JSONAssert.assertEquals
import org.skyscreamer.jsonassert.JSONCompareMode
import kotlin.test.Test

class PlayerRoutingTest {

    private fun Application.testModule(playersService: PlayersService, rankingService: RankingService) {
        install(Koin) {
            modules(module {
                single<PlayersService> { playersService }
                single<RankingService> { rankingService }
            })
        }
        playersRouting()
    }

    @Test
    fun `Should get a player`() = testApplication {
        val playersService = mockk<PlayersService>()
        every { playersService.get("1") } returns Player("1", "aRandomPseudo", 5)

        val rankingService = mockk<RankingService>()
        every { rankingService.get(Player("1", "aRandomPseudo", 5)) } returns Ranking(4)
        application { testModule(playersService, rankingService) }

        val response = client.get("/players/1")

        assertEquals(HttpStatusCode.OK, response.status)
        @Language("JSON")
        val expected = """{
            "id": "1",
            "pseudo": "aRandomPseudo",
            "score": 5,
            "rank" : 4
            }"""
        assertEquals(expected, response.bodyAsText(), JSONCompareMode.STRICT)
        verify(exactly = 1) { playersService.get(any()) }
    }

    @Test
    fun `Should return an error when a player is not got`() = testApplication {
        val playersService = mockk<PlayersService>()
        every { playersService.get(any()) } returns NOT_FOUND
        application { testModule(playersService, mockk<RankingService>()) }

        val response = client.get("/players/1")

        assertEquals(HttpStatusCode.NotFound, response.status)
    }

    @Test
    fun `Should create a player`() = testApplication {
        val playersService = mockk<PlayersService>()
        every { playersService.create("aRandomPseudo") } returns Player("1", "aRandomPseudo", 0)
        application { testModule(playersService, mockk<RankingService>()) }

        @Language("JSON")
        val body = """{
            "pseudo": "aRandomPseudo"
            }"""
        val response = client.post("/players") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }

        assertEquals(HttpStatusCode.Created, response.status)
        assertEquals("/players/1", response.headers[HttpHeaders.Location])
        verify(exactly = 1) { playersService.create(any()) }
    }

    @Test
    fun `Should update a player`() = testApplication {
        val playersService = mockk<PlayersService>()
        every { playersService.update("1", 10) } returns Player("1", "aRandomPseudo", 10)
        application { testModule(playersService, mockk<RankingService>()) }

        @Language("JSON")
        val body = """{
              "score": 10
            }"""
        val response = client.patch("/players/1") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }
        assertEquals(HttpStatusCode.NoContent, response.status)
        verify(exactly = 1) { playersService.update(any(), any()) }
    }

    @Test
    fun `Should return an error when player id to update is not provided`() = testApplication {
        application { testModule(mockk<PlayersService>(), mockk<RankingService>()) }

        @Language("JSON")
        val body = """{
              "score": 10
            }"""
        val response = client.patch("/players") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }
        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun `Should return an error when player id to update is not found`() = testApplication {
        val playersService = mockk<PlayersService>()
        every { playersService.update("1", 10) } returns NOT_FOUND
        application { testModule(playersService, mockk<RankingService>()) }

        @Language("JSON")
        val body = """{
              "score": 10
            }"""
        val response = client.patch("/players/1") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }
        assertEquals(HttpStatusCode.NotFound, response.status)
    }

    @Test
    fun `Should delete all players`() = testApplication {
        val playersService = mockk<PlayersService>()
        every { playersService.deleteAll() } just runs
        application { testModule(playersService, mockk<RankingService>()) }

        val response = client.delete("/players")
        assertEquals(HttpStatusCode.NoContent, response.status)
        verify(exactly = 1) { playersService.deleteAll() }
    }

    @Test
    fun `Should find all players ordered by score desc`() = testApplication {
        val playersService = mockk<PlayersService>()
        every { playersService.findAll() } returns listOf(
            Player("1", "second", 10),
            Player("2", "first", 20)
        )
        application { testModule(playersService, mockk<RankingService>()) }

        val response = client.get("/players")

        assertEquals(HttpStatusCode.OK, response.status)
        @Language("JSON")
        val expected = """[
                {
                    "id": "2",
                    "pseudo": "first",
                    "score": 20
                },
                {
                    "id": "1",
                    "pseudo": "second",
                    "score": 10
                }
            ]"""
        assertEquals(expected, response.bodyAsText(), JSONCompareMode.STRICT)
        verify(exactly = 1) { playersService.findAll() }
    }
}