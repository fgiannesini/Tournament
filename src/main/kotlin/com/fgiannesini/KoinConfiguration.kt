package com.fgiannesini

import com.fgiannesini.domain.PlayerIdGenerator
import com.fgiannesini.domain.PlayersService
import com.fgiannesini.domain.RankingService
import com.fgiannesini.domain.dynamoDbModule
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(module {
            single<PlayerIdGenerator> { PlayerIdGenerator() }
            single<PlayersService> { PlayersService(get(), get()) }
            single<RankingService> { RankingService(get()) }
        }, dynamoDbModule)
    }

}
