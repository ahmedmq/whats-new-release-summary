package com.ahmedmq.whatsnew.release.summary.persistence

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.getItem
import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import aws.sdk.kotlin.services.dynamodb.putItem
import aws.sdk.kotlin.services.dynamodb.query
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Repository

const val WHATS_NEW_TABLE_NAME = "whats-new"

@Repository
class WhatsNewRepository(private val dynamoDbClient: DynamoDbClient) {

    fun save(whatsNew: WhatsNew)  = runBlocking {
        dynamoDbClient.putItem {
            tableName = WHATS_NEW_TABLE_NAME
            item = whatsNew.toAttributeValues()
        }
    }

    fun findAllByProject(projectId: Int): List<WhatsNew> = runBlocking {
        dynamoDbClient.query {
            tableName = WHATS_NEW_TABLE_NAME
            keyConditionExpression = "projectId = :id"
            expressionAttributeValues = mapOf(
                ":id" to AttributeValue.N(projectId.toString()),
            )
        }.items?.map { it.toWhatsNew() } ?: emptyList()
    }

    fun findByProjectAndRelease(projectId: Int, releaseId: Int): WhatsNew? = runBlocking {
        dynamoDbClient.getItem {
            tableName = WHATS_NEW_TABLE_NAME
            key = mapOf(
                "projectId" to AttributeValue.N(projectId.toString())
            )
        }.item?.toWhatsNew()
    }

}