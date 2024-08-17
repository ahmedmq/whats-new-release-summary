package com.ahmedmq.whatsnew.release.summary.persistence

import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import java.time.LocalDateTime

data class WhatsNew(
    val releaseId: Int,
    val projectId: Int,
    val acceptedDate: LocalDateTime,
    val name: String,
    val projectName: String,
    val content: String,
)

fun WhatsNew.toAttributeValues(): Map<String, AttributeValue> = mapOf(
    "releaseId" to AttributeValue.N(releaseId.toString()),
    "projectId" to AttributeValue.N(projectId.toString()),
    "acceptedDate" to AttributeValue.S(acceptedDate.toString()),
    "name" to AttributeValue.S(name),
    "projectName" to AttributeValue.S(projectName),
    "content" to AttributeValue.S(content),

)

fun Map<String, AttributeValue>.toWhatsNew(): WhatsNew = WhatsNew(
    releaseId = this["releaseId"]?.asN()?.toInt() ?: error("Missing releaseId"),
    projectId = this["projectId"]?.asN()?.toInt() ?: error("Missing projectId"),
    acceptedDate = this["acceptedDate"]?.asS()?.let(LocalDateTime::parse) ?: error("Missing or invalid releaseDate"),
    name = this["name"]?.asS() ?: "",
    projectName = this["projectName"]?.asS() ?: error(""),
    content = this["content"]?.asS() ?: "",
)
