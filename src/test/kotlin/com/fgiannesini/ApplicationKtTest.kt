package com.fgiannesini

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Assertions.*
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
 }