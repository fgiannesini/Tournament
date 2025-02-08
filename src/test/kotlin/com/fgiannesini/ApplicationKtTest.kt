package com.fgiannesini

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Assertions.assertEquals
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import kotlin.test.Test

class ApplicationKtTest {

    @Test
    fun `Should return hello world when calling root`() = testApplication {
        application {
            module()
        }
        val response = client.get("/")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("Hello World!", response.bodyAsText())
    }

    @Test
    fun `Should get a player`() = testApplication {
        application {
            module()
        }

        val response = client.get("/player/1")

        assertEquals(HttpStatusCode.OK, response.status)
        @Language("JSON")
        val expected = """{
            "id": 1,
            "pseudo": "toto"
            }"""
        JSONAssert.assertEquals(expected, response.bodyAsText(), JSONCompareMode.STRICT)
    }
}