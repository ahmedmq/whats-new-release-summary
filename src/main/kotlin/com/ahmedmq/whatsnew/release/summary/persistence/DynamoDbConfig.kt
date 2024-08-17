package com.ahmedmq.whatsnew.release.summary.persistence

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.smithy.kotlin.runtime.net.url.Url
import kotlinx.coroutines.runBlocking
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class DynamoDbConfig(val environment: Environment) {

    @Bean
    fun dynamoDbClient(): DynamoDbClient {
        return when {
            environment.activeProfiles.any { it in listOf("local", "e2e") } -> {
                runBlocking {
                    DynamoDbClient.fromEnvironment {
                        endpointUrl = Url.parse("http://localhost:8000")
                    }
                }
            }
            else -> {
                runBlocking {
                    DynamoDbClient.fromEnvironment()
                }
            }
        }
    }
}
