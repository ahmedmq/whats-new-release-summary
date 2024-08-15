package com.ahmedmq.whatsnew.release.summary.persistence

import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

class WhatsNewTest {

    @Test
    fun `to attribute values`() {
        val requiredEntries: Map<String, AttributeValue> = mapOf(
            "releaseId" to AttributeValue.N("1"),
            "projectId" to AttributeValue.N("1"),
            "releaseDate" to AttributeValue.S("2024-01-01")
        )
        val whatsNew = WhatsNew(1, 1, LocalDate.of(2024, 1, 1))

        val toAttributeValues: Map<String, AttributeValue> = whatsNew.toAttributeValues()

        assertThat(toAttributeValues).containsExactlyEntriesOf(requiredEntries)
    }

    @Test
    fun `to whats new`() {
        val requiredEntries: Map<String, AttributeValue> = mapOf(
            "releaseId" to AttributeValue.N("1"),
            "projectId" to AttributeValue.N("1"),
            "releaseDate" to AttributeValue.S("2024-01-01")
        )

        val whatsNew = requiredEntries.toWhatsNew()

        assertThat(whatsNew.projectId).isEqualTo(1)
        assertThat(whatsNew.releaseId).isEqualTo(1)
        assertThat(whatsNew.releaseDate).isEqualTo(LocalDate.of(2024, 1, 1))
    }
}