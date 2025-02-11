package com.fgiannesini

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.intellij.lang.annotations.Language
import java.util.*
import kotlin.test.Test


class AddData {

    @Test
    fun `Add data to database`() = runBlocking {
        repeat(100) {
            val client = HttpClient(CIO)
            List(100) {
                async {
                    @Language("JSON")
                    val body = """{
                    "pseudo": "${UUID.randomUUID()}"
                    }"""
                    client.post("http://localhost:8080/players") {
                        contentType(ContentType.Application.Json)
                        setBody(body)
                    }
                }
            }.awaitAll()
            client.close()
        }
    }
}