package com.ahmedmq.whatsnew.release.summary.persistence

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.smithy.kotlin.runtime.net.url.Url
import kotlinx.coroutines.runBlocking
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DynamoDbConfig {

    @Bean
    fun dynamoDbClient(): DynamoDbClient {
        return when (System.getenv("SPRING_PROFILES_ACTIVE")) {
            "local", "e2e" -> {
                runBlocking {
                    println("Setting DynamoDB for Local")
                    DynamoDbClient.fromEnvironment {
                        endpointUrl = Url.parse("http://localhost:8000")
                    }
                }
            }

            else -> {
                runBlocking {
                    println("Setting DynamoDB for Prod")
                    DynamoDbClient.fromEnvironment()
                }
            }
        }
    }
}