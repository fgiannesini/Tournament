package com.fgiannesini

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        get("/player/1") {
            call.respond(PlayerInformation(1, "toto"))
        }
        post("/player") {
            val playerInformation = call.receive<PlayerCreation>()
            call.respond(PlayerInformation(1, playerInformation.pseudo))
        }
    }
}
