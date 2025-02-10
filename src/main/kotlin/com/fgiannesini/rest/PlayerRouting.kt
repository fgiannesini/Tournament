package com.fgiannesini.rest

import com.fgiannesini.domain.PlayerService
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.playerRouting() {
    val playerService: PlayerService by inject()
    install(ContentNegotiation) {
        json()
    }
    routing {
        get("/player/{playerId}") {
            val playerId = call.parameters["playerId"]
            val player = playerService.get(playerId!!)
            call.respond(PlayerInformation.from(player))
        }
        post("/player") {
            val playerCreation = call.receive<PlayerCreation>()
            val player = playerService.create(playerCreation.pseudo)
            call.response.headers.append(HttpHeaders.Location, "/player/${player.id}")
            call.respond(HttpStatusCode.Created)
        }
        patch("/player/{playerId}") {
            val playerId = call.parameters["playerId"]
            val playerUpdate = call.receive<PlayerUpdate>()
            val player = playerService.update(playerId!!, playerUpdate.points)
            call.respond(HttpStatusCode.NoContent)
        }
    }
}
