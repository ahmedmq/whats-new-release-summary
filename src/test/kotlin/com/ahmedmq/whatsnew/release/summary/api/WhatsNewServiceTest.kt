package com.ahmedmq.whatsnew.release.summary.api

import com.ahmedmq.whatsnew.release.summary.client.ReleaseResponse
import com.ahmedmq.whatsnew.release.summary.client.TrackerClient
import com.ahmedmq.whatsnew.release.summary.persistence.WhatsNew
import com.ahmedmq.whatsnew.release.summary.persistence.WhatsNewRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class WhatsNewServiceTest {

    val mockTrackerClient = mockk<TrackerClient>()
    val mockWhatsNewRepository = mockk<WhatsNewRepository>()
    val whatsNewService = WhatsNewService(mockWhatsNewRepository, mockTrackerClient)

    @Test
    fun `whats new for project should return from dynamodb if present`() {
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
            ReleaseResponse(1, "Release1", LocalDateTime.now()),
            ReleaseResponse(2, "Release2", LocalDateTime.now()),
        )
        every { mockWhatsNewRepository.findByProjectIdAndReleaseId(1, 2) } returns
            WhatsNew(
                2,
                1,
                LocalDateTime.of(2024, 1, 1, 0, 0, 0),
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
