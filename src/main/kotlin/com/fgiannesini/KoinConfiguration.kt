package com.fgiannesini

import com.fgiannesini.domain.PlayerPersistence
import com.fgiannesini.domain.PlayerService
import com.fgiannesini.infrastructure.PlayerPersistenceImpl
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(module {
            single<PlayerService> { PlayerService(get()) }
            single<PlayerPersistence> { PlayerPersistenceImpl() }
        })
    }
}
