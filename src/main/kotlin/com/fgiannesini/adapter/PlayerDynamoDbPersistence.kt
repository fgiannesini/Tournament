package com.fgiannesini.adapter

import com.fgiannesini.domain.NOT_FOUND
import com.fgiannesini.domain.Player
import com.fgiannesini.domain.PlayerPersistence
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.*

class PlayerDynamoDbPersistence(private val dynamoDbClient: DynamoDbClient) : PlayerPersistence {

    private val tableName = "Player"

    init {
        createTableIfNotExists()
    }

    private fun createTableIfNotExists() {
        dynamoDbClient.createTable(
            CreateTableRequest.builder().tableName(tableName)
                .keySchema(KeySchemaElement.builder().attributeName("id").keyType(KeyType.HASH).build())
                .attributeDefinitions(
                    AttributeDefinition.builder().attributeName("id").attributeType(ScalarAttributeType.S).build()
                ).billingMode(BillingMode.PAY_PER_REQUEST).build()
        )
    }

    override fun findBy(playerId: String): Player {
        val getItemRequest =
            GetItemRequest.builder().tableName(tableName).key(mapOf("id" to AttributeValue.fromS(playerId))).build()

        val response = dynamoDbClient.getItem(getItemRequest)
        val item = response.item()
        return when {
            item.isNotEmpty() -> toPlayer(item)
            else -> NOT_FOUND
        }
    }

    private fun toPlayer(item: MutableMap<String, AttributeValue>) = Player(
        item["id"]?.s()!!,
        item["pseudo"]?.s()!!,
        item["score"]?.n()?.toInt()!!,
    )

    override fun save(player: Player) {
        val putItemRequest = PutItemRequest.builder().tableName(tableName).item(
            mapOf(
                "id" to AttributeValue.fromS(player.id),
                "pseudo" to AttributeValue.fromS(player.pseudo),
                "score" to AttributeValue.fromN(player.score.toString())
            )
        ).build()
        dynamoDbClient.putItem(putItemRequest)
    }

    override fun deleteAll(limit: Int) {
        var lastEvaluatedKey: Map<String, AttributeValue>? = null
        do {
            val scanRequest =
                ScanRequest.builder()
                    .tableName(tableName)
                    .limit(limit)
                    .exclusiveStartKey(lastEvaluatedKey)
                    .build()

            val scanResponse = dynamoDbClient.scan(scanRequest)
            val deleteRequests = scanResponse.items().map {
                WriteRequest.builder()
                    .deleteRequest(
                        DeleteRequest.builder()
                            .key(mapOf("id" to it["id"]!!))
                            .build()
                    )
                    .build()
            }

            deleteRequests.chunked(25).forEach { chunk ->
                val batchWriteRequest = BatchWriteItemRequest.builder().requestItems(mapOf(tableName to chunk)).build()
                dynamoDbClient.batchWriteItem(batchWriteRequest)
            }

            lastEvaluatedKey = scanResponse.lastEvaluatedKey()?.takeIf { it.isNotEmpty() }
        } while (lastEvaluatedKey != null)

    }

    override fun findAll(limit: Int): List<Player> {
        val allItems = mutableListOf<Player>()
        var lastEvaluatedKey: Map<String, AttributeValue>? = null
        do {
            val scanRequest =
                ScanRequest.builder()
                    .tableName(tableName)
                    .limit(limit)
                    .exclusiveStartKey(lastEvaluatedKey)
                    .build()

            val scanResponse = dynamoDbClient.scan(scanRequest)
            allItems.addAll(scanResponse.items().map { toPlayer(it) })
            lastEvaluatedKey = scanResponse.lastEvaluatedKey()?.takeIf { it.isNotEmpty() }
        } while (lastEvaluatedKey != null)
        return allItems
    }
}