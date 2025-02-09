package com.fgiannesini.adapter


import org.junit.AfterClass
import org.junit.BeforeClass
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.utility.DockerImageName
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import kotlin.test.Test
import kotlin.test.assertNull

class PlayerDynamoDbPersistenceTest {
    companion object {
        private val localStack: LocalStackContainer =
            LocalStackContainer(DockerImageName.parse("localstack/localstack:latest"))
                .withServices(LocalStackContainer.Service.DYNAMODB)

        private lateinit var dynamoDbClient: DynamoDbClient
        private lateinit var playerPersistence: PlayerDynamoDbPersistence

        @BeforeClass
        @JvmStatic
        fun setUp() {
            localStack.start()

            dynamoDbClient = DynamoDbClient.builder()
                .region(Region.EU_WEST_1)
                .credentialsProvider(
                    StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("fakeAccessKey", "fakeSecretKey")
                    )
                )
                .endpointOverride(localStack.getEndpointOverride(LocalStackContainer.Service.DYNAMODB))
                .build()

            playerPersistence = PlayerDynamoDbPersistence(dynamoDbClient)
        }

        @AfterClass
        @JvmStatic
        fun tearDown() {
            localStack.stop()
        }
    }

    @Test
    fun `test find non-existing user`() {
        val retrievedUser = playerPersistence.findBy(99)
        assertNull(retrievedUser)
    }
}