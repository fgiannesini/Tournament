package com.fgiannesini.domain

import com.fgiannesini.adapter.PlayerDynamoDbPersistence
import org.koin.dsl.module
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.utility.DockerImageName
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient

val dynamoDbModule = module {
    val localStack: LocalStackContainer =
        LocalStackContainer(DockerImageName.parse("localstack/localstack:latest"))
            .withServices(LocalStackContainer.Service.DYNAMODB)
    localStack.start()
    single<DynamoDbClient> {
        DynamoDbClient.builder()
            .region(Region.EU_WEST_1)
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create("fakeAccessKey", "fakeSecretKey")
                )
            )
            .endpointOverride(localStack.getEndpointOverride(LocalStackContainer.Service.DYNAMODB))
            .build()
    }
    single<PlayerPersistence> { PlayerDynamoDbPersistence(get()) }
}