package com.fgiannesini

import com.fgiannesini.domain.PlayerService
import com.fgiannesini.rest.playerRouting
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureKoin()
    playerRouting(PlayerService())
}
