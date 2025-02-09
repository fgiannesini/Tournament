package com.fgiannesini.adapter

import com.fgiannesini.domain.Player
import com.fgiannesini.domain.PlayerPersistence
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.*

class PlayerDynamoDbPersistence(private val dynamoDbClient: DynamoDbClient) : PlayerPersistence {

    private val tableName = "Users"

    init {
        createTableIfNotExists()
    }

    private fun createTableIfNotExists() {
        try {
            dynamoDbClient.createTable(
                CreateTableRequest.builder()
                    .tableName(tableName)
                    .keySchema(KeySchemaElement.builder().attributeName("id").keyType(KeyType.HASH).build())
                    .attributeDefinitions(
                        AttributeDefinition.builder().attributeName("id").attributeType(ScalarAttributeType.S).build()
                    )
                    .billingMode(BillingMode.PAY_PER_REQUEST)
                    .build()
            )
        } catch (e: ResourceInUseException) {
            // La table existe déjà
        }
    }
    override fun findBy(playerId: Long): Player {
//        val getItemRequest = GetItemRequest.builder()
//            .tableName(tableName)
//            .key(mapOf("id" to AttributeValue.builder().s(id).build()))
//            .build()
//
//        val response = dynamoDbClient.getItem(getItemRequest)
//        return response.item()?.let {
//            User(it["id"]!!.s(), it["name"]!!.s())
//        }
        return Player(playerId, "toto")
    }
}