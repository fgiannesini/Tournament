package com.fgiannesini

import com.fgiannesini.adapter.PlayerDynamoDbPersistence
import com.fgiannesini.domain.PlayerIdGenerator
import com.fgiannesini.domain.PlayerPersistence
import com.fgiannesini.domain.PlayerService
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import java.net.URI

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(module {
            single<DynamoDbClient> {
                DynamoDbClient.builder()
                    .region(Region.EU_WEST_1)
                    .credentialsProvider(
                        StaticCredentialsProvider.create(
                            AwsBasicCredentials.create("fakeAccessKey", "fakeSecretKey")
                        )
                    )
                    .endpointOverride(URI.create("http://localhost:4566"))
                    .build()
            }
            single<PlayerPersistence> { PlayerDynamoDbPersistence(get()) }
            single<PlayerIdGenerator> { PlayerIdGenerator() }
            single<PlayerService> { PlayerService(get(), get()) }
        })
    }
}
