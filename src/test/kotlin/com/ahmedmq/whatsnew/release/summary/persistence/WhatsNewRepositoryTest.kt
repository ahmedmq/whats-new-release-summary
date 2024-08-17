package com.ahmedmq.whatsnew.release.summary.persistence

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.*
import com.ahmedmq.whatsnew.release.summary.aWhatsNew
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class WhatsNewRepositoryTest {

    private val dynamoDbClient = mockk<DynamoDbClient>()
    private val whatsNewRepository = WhatsNewRepository(dynamoDbClient)
    private val testWhatsNew = aWhatsNew()

    @Test
    fun `save whats new`() {
        val putItemRequestSlot = slot<PutItemRequest>()
        every {
            runBlocking {
                dynamoDbClient.putItem(capture(putItemRequestSlot))
            }
        } returns PutItemResponse {}

        whatsNewRepository.save(testWhatsNew)

        with(putItemRequestSlot.captured) {
            assertEquals(WHATS_NEW_TABLE_NAME, tableName)
            assertEquals(testWhatsNew.toAttributeValues(), item)
        }
    }

    @Test
    fun `whats new for project exists`() {
        val queryItemRequestSlot = slot<QueryRequest>()
        every {
            runBlocking {
                dynamoDbClient.query(capture(queryItemRequestSlot))
            }
        } returns QueryResponse {
            items = listOf(testWhatsNew.toAttributeValues())
        }

        val whatsNewList = whatsNewRepository.findAllByProject(1)

        with(queryItemRequestSlot.captured) {
            assertEquals(WHATS_NEW_TABLE_NAME, tableName)
            assertEquals("projectId = :id", keyConditionExpression)
            assertEquals(mapOf(":id" to AttributeValue.N("1")), expressionAttributeValues)
        }
        assertEquals(listOf(testWhatsNew), whatsNewList)
    }

    @Test
    fun `whats new for project does not exists`() {
        every {
            runBlocking {
                dynamoDbClient.query(any())
            }
        } returns QueryResponse {
            items = null
        }

        val whatsNewList = whatsNewRepository.findAllByProject(1)

        assertEquals(emptyList(), whatsNewList)
    }

    @Test
    fun `whats new for project release`() {
        val getItemRequestSlot = slot<GetItemRequest>()
        every {
            runBlocking {
                dynamoDbClient.getItem(capture(getItemRequestSlot))
            }
        } returns GetItemResponse {
            item = testWhatsNew.toAttributeValues()
        }

        val whatsNew = whatsNewRepository.findByProjectIdAndReleaseId(1, 1)

        with(getItemRequestSlot.captured) {
            assertEquals(WHATS_NEW_TABLE_NAME, tableName)
            assertEquals(
                mapOf(
                    "projectId" to AttributeValue.N("1"),
                    "releaseId" to AttributeValue.N("1"),
                ),
                key,
            )
        }
        assertEquals(testWhatsNew, whatsNew)
    }
}
