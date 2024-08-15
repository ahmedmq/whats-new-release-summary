package com.ahmedmq.whatsnew.release.summary.persistence

import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import java.time.LocalDate

data class WhatsNew(
    val releaseId: Int,
    val projectId: Int,
    val releaseDate: LocalDate
)

fun WhatsNew.toAttributeValues(): Map<String, AttributeValue> = mapOf(
    "releaseId" to AttributeValue.N(releaseId.toString()),
    "projectId" to AttributeValue.N(projectId.toString()),
    "releaseDate" to AttributeValue.S(releaseDate.toString())
)

fun Map<String, AttributeValue>.toWhatsNew(): WhatsNew = WhatsNew(
    releaseId = this["releaseId"]?.asN()?.toInt() ?: error("Missing releaseId"),
    projectId = this["projectId"]?.asN()?.toInt()?: error("Missing projectId"),
    releaseDate = this["releaseDate"]?.asS()?.let(LocalDate::parse) ?: error("Missing or invalid releaseDate")
)