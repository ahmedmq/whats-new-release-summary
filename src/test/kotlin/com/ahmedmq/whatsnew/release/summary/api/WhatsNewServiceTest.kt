package com.ahmedmq.whatsnew.release.summary.api

import com.ahmedmq.whatsnew.release.summary.client.tracker.ReleaseResponse
import com.ahmedmq.whatsnew.release.summary.client.tracker.TrackerClient
import com.ahmedmq.whatsnew.release.summary.persistence.WhatsNew
import com.ahmedmq.whatsnew.release.summary.persistence.WhatsNewRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.ai.chat.client.ChatClient
import java.time.Instant

class WhatsNewServiceTest {

    val mockTrackerClient = mockk<TrackerClient>()
    val mockWhatsNewRepository = mockk<WhatsNewRepository>()
    val mockChatClientBuilder = mockk<ChatClient.Builder>()
    val mockChatClient = mockk<ChatClient>()
    val whatsNewService = WhatsNewService(mockWhatsNewRepository, mockTrackerClient, mockChatClientBuilder)

    @Test
    fun `whats new for project should return from dynamodb if present`() {
        every { mockChatClientBuilder.build() } returns mockChatClient
        every {
            mockTrackerClient.releases(
                "token",
                1,
                mapOf(
                    "fields" to "id,name,accepted_at",
                    "with_state" to "accepted",
                ),
            )
        } returns listOf(
            ReleaseResponse(1, "Release1", Instant.now()),
            ReleaseResponse(2, "Release2", Instant.now()),
        )
        every { mockWhatsNewRepository.findByProjectIdAndReleaseId(1, 2) } returns
            WhatsNew(
                2,
                1,
                Instant.parse("2024-01-01T01:01:01.00Z"),
                "",
                "",
                "",
            )

        whatsNewService.getWhatsNewForProject(WhatsNewRequest("token", 1, "apiKey"))

        verify(exactly = 1) {
            mockTrackerClient.releases(
                "token",
                1,
                mapOf(
                    "fields" to "id,name,accepted_at",
                    "with_state" to "accepted",
                ),
            )
        }

        verify(exactly = 1) {
            mockWhatsNewRepository.findByProjectIdAndReleaseId(
                1,
                2,
            )
        }
    }
}
