package com.ahmedmq.whatsnew.release.summary.persistence

import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import kotlin.test.assertEquals

class WhatsNewTest {

    @Test
    fun `to attribute values`() {
        val requiredEntries: Map<String, AttributeValue> = mapOf(
            "releaseId" to AttributeValue.N("1"),
            "projectId" to AttributeValue.N("1"),
            "acceptedDate" to AttributeValue.S("2024-01-01T00:00"),
            "name" to AttributeValue.S("name"),
            "projectName" to AttributeValue.S("projectName"),
            "content" to AttributeValue.S("content"),
        )
        val whatsNew = WhatsNew(
            1,
            1,
            LocalDateTime.of(2024, 1, 1, 0, 0, 0),
            "name",
            "projectName",
            "content",
        )

        val toAttributeValues: Map<String, AttributeValue> = whatsNew.toAttributeValues()

        assertThat(toAttributeValues).containsExactlyEntriesOf(requiredEntries)
    }

    @Test
    fun `to whats new`() {
        val requiredEntries: Map<String, AttributeValue> = mapOf(
            "releaseId" to AttributeValue.N("1"),
            "projectId" to AttributeValue.N("1"),
            "acceptedDate" to AttributeValue.S("2024-01-01T00:00"),
            "name" to AttributeValue.S("name"),
            "projectName" to AttributeValue.S("projectName"),
            "content" to AttributeValue.S("content"),
        )

        val whatsNew = requiredEntries.toWhatsNew()

        assertEquals(1, whatsNew.projectId)
        assertEquals(1, whatsNew.releaseId)
        assertEquals(
            LocalDateTime.of(2024, 1, 1, 0, 0, 0),
            whatsNew.acceptedDate,
        )
        assertEquals("name", whatsNew.name)
        assertEquals("projectName", whatsNew.projectName)
        assertEquals("content", whatsNew.content)
    }
}
