package com.fgiannesini.rest

import com.fgiannesini.domain.NOT_FOUND
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
        get("/players") {
            val players = playerService.findAll().map { PlayerInformation.from(it) }
            call.respond(players)
        }
        get("/players/{playerId}") {
            val playerId = call.parameters["playerId"]
            when (val player = playerService.get(playerId!!)) {
                NOT_FOUND -> call.respond(HttpStatusCode.NotFound)
                else -> call.respond(PlayerInformation.from(player))
            }
        }
        post("/players") {
            val playerCreation = call.receive<PlayerCreation>()
            val player = playerService.create(playerCreation.pseudo)
            call.response.headers.append(HttpHeaders.Location, "/players/${player.id}")
            call.respond(HttpStatusCode.Created)
        }
        patch("/players/{playerId}") {
            val playerId = call.parameters["playerId"]
            val playerUpdate = call.receive<PlayerUpdate>()
            when (playerService.update(playerId!!, playerUpdate.score)) {
                NOT_FOUND -> call.respond(HttpStatusCode.NotFound)
                else -> call.respond(HttpStatusCode.NoContent)
            }
        }
        patch("/players") {
            call.respond(HttpStatusCode.BadRequest, "Player id is required : /player/{playerId}")
        }
        delete("/players") {
            playerService.deleteAll()
            call.respond(HttpStatusCode.NoContent)
        }
    }
}
