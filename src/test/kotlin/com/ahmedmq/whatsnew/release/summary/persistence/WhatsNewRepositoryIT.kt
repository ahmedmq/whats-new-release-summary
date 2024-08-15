package com.ahmedmq.whatsnew.release.summary.persistence

import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.createTable
import aws.sdk.kotlin.services.dynamodb.model.AttributeDefinition
import aws.sdk.kotlin.services.dynamodb.model.KeySchemaElement
import aws.sdk.kotlin.services.dynamodb.model.KeyType
import aws.sdk.kotlin.services.dynamodb.model.ScalarAttributeType
import aws.sdk.kotlin.services.dynamodb.putItem
import aws.smithy.kotlin.runtime.net.url.Url
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName
import java.time.LocalDate
import kotlin.test.assertContentEquals

@SpringBootTest
@TestMethodOrder(OrderAnnotation::class)
class WhatsNewRepositoryIT {

    companion object {
        private var dynamoDbContainer = GenericContainer<Nothing>(
            DockerImageName.parse("public.ecr.aws/aws-dynamodb-local/aws-dynamodb-local:latest")
                .asCompatibleSubstituteFor("amazon/dynamodb-local")
        ).apply {
            withExposedPorts(8000)
            withEnv("AWS_ACCESS_KEY_ID", "X")
            withEnv("AWS_SECRET_ACCESS_KEY", "X")
        }
    }

    init {
        dynamoDbContainer.start()
    }

    @Autowired
    lateinit var whatsNewRepository: WhatsNewRepository

    @Test
    @Order(1)
    fun `get whats new for project`() {
        val whatsNewList = whatsNewRepository.findAllByProject(1)

        assertContentEquals(
            listOf(
                WhatsNew(
                    1,
                    1,
                    LocalDate.of(2024, 1, 1)
                )
            ), whatsNewList
        )
    }

    @Test
    @Order(2)
    fun `save whats new`() {
        whatsNewRepository.save(
            WhatsNew(
                1,
                1,
                LocalDate.of(2023, 2, 2)
            )
        )
    }

    @TestConfiguration
    class DynamoDBTestConfig {

        @Bean
        @Primary
        fun dynamoDbClientTest(): DynamoDbClient =
            runBlocking {
                DynamoDbClient.fromEnvironment {
                    region = "ap-northeast-1"
                    endpointUrl =
                        Url.parse("http://${dynamoDbContainer.host}:${dynamoDbContainer.firstMappedPort}")
                    credentialsProvider = StaticCredentialsProvider {
                        accessKeyId = "XX"
                        secretAccessKey = "XX"
                    }
                }
            }


        @Bean
        fun clr(dynamoDbClient: DynamoDbClient): CommandLineRunner {
            return CommandLineRunner {
                runBlocking {
                    dynamoDbClient.createTable {
                        tableName = WHATS_NEW_TABLE_NAME
                        keySchema = listOf(
                            KeySchemaElement {
                                attributeName = "projectId"
                                keyType = KeyType.Hash
                            },
                            KeySchemaElement {
                                attributeName = "releaseId"
                                keyType = KeyType.Range
                            },
                        )
                        attributeDefinitions = listOf(
                            AttributeDefinition {
                                attributeName = "projectId"
                                attributeType = ScalarAttributeType.N
                            },
                            AttributeDefinition {
                                attributeName = "releaseId"
                                attributeType = ScalarAttributeType.N
                            },
                        )
                        provisionedThroughput {
                            readCapacityUnits = 10
                            writeCapacityUnits = 10
                        }
                    }

                    dynamoDbClient.putItem {
                        tableName = WHATS_NEW_TABLE_NAME
                        item = WhatsNew(
                            1,
                            1,
                            LocalDate.of(2024, 1, 1)
                        ).toAttributeValues()
                    }
                }
            }
        }
    }

}