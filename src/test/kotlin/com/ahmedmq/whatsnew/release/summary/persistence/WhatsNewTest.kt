package com.ahmedmq.whatsnew.release.summary.persistence

import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import com.ahmedmq.whatsnew.release.summary.aWhatsNew
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.Instant
import kotlin.test.assertEquals

class WhatsNewTest {

    @Test
    fun `to attribute values`() {
        val requiredEntries: Map<String, AttributeValue> = mapOf(
            "releaseId" to AttributeValue.N("1"),
            "projectId" to AttributeValue.N("1"),
            "acceptedDate" to AttributeValue.S("2024-01-01T01:01:01Z"),
            "name" to AttributeValue.S("Test Release"),
            "projectName" to AttributeValue.S("Project X"),
            "content" to AttributeValue.S("GenAI"),
        )
        val whatsNew = aWhatsNew()

        val toAttributeValues: Map<String, AttributeValue> = whatsNew.toAttributeValues()

        assertThat(toAttributeValues).containsExactlyEntriesOf(requiredEntries)
    }

    @Test
    fun `to whats new`() {
        val requiredEntries: Map<String, AttributeValue> = mapOf(
            "releaseId" to AttributeValue.N("1"),
            "projectId" to AttributeValue.N("1"),
            "acceptedDate" to AttributeValue.S("2024-01-01T01:01:01.00Z"),
            "name" to AttributeValue.S("name"),
            "projectName" to AttributeValue.S("projectName"),
            "content" to AttributeValue.S("content"),
        )

        val whatsNew = requiredEntries.toWhatsNew()

        assertEquals(1, whatsNew.projectId)
        assertEquals(1, whatsNew.releaseId)
        assertEquals(
            Instant.parse("2024-01-01T01:01:01.00Z"),
            whatsNew.acceptedAt,
        )
        assertEquals("name", whatsNew.name)
        assertEquals("projectName", whatsNew.projectName)
        assertEquals("content", whatsNew.content)
    }
}
