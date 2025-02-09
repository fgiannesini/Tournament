package com.fgiannesini.rest

import com.fgiannesini.domain.PlayerService
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.playerRouting(playerService: PlayerService) {
    install(ContentNegotiation) {
        json()
    }
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        get("/player/{playerId}") {
            val playerId = call.parameters["playerId"]?.toLongOrNull() ?: 0
            val player = playerService.get(playerId)
            call.respond(PlayerInformation.from(player!!))
        }
        post("/player") {
            val playerInformation = call.receive<PlayerCreation>()
            call.respond(PlayerInformation(1, playerInformation.pseudo))
        }
    }
}
