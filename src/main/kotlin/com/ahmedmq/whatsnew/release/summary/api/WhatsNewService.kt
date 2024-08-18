package com.ahmedmq.whatsnew.release.summary.api

import com.ahmedmq.whatsnew.release.summary.client.tracker.ReleaseResponse
import com.ahmedmq.whatsnew.release.summary.client.tracker.TrackerClient
import com.ahmedmq.whatsnew.release.summary.persistence.WhatsNew
import com.ahmedmq.whatsnew.release.summary.persistence.WhatsNewRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.ai.chat.client.ChatClient.Builder
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class WhatsNewService(
    val whatsNewRepository: WhatsNewRepository,
    val trackerClient: TrackerClient,
    val chatClientBuilder: Builder,
) {

    @Value("classpath:prompts/whats-new-release.st")
    lateinit var whatsNewPrompt: Resource

    fun getWhatsNewForProject(whatsNewRequest: WhatsNewRequest): List<WhatsNew> {
        val releases = trackerClient.releases(
            whatsNewRequest.apiToken,
            whatsNewRequest.projectId,
            mapOf(
                "fields" to "id,name,accepted_at",
                "with_state" to "accepted",
            ),
        )

        return releases.reversed().mapIndexed { index, r ->
            if (index == 0) {
                whatsNewRepository.findByProjectIdAndReleaseId(
                    whatsNewRequest.projectId,
                    releases.last().id,
                ) ?: getReleaseStories(
                    whatsNewRequest.apiToken,
                    whatsNewRequest.projectId,
                    r,
                )
            } else {
                WhatsNew(
                    r.id,
                    whatsNewRequest.projectId,
                    r.acceptedAt,
                    r.name,
                    "",
                    "",
                )
            }
        }
    }

    fun getReleaseStories(apiToken: String, projectId: Int, releaseResponse: ReleaseResponse): WhatsNew {
        val stories = trackerClient.releaseStories(
            apiToken,
            projectId,
            releaseResponse.id,
            mapOf(
                "fields" to "id,name,description,story_type,labels(name)",
            ),
        )

        val project = trackerClient.project(apiToken, projectId)

        val storiesAsJsonString = ObjectMapper().writeValueAsString(stories.take(2))
        println("Stories: $storiesAsJsonString")
        val content = summarize(storiesAsJsonString)
        println("Content: $content")
        return WhatsNew(
            releaseResponse.id,
            projectId,
            releaseResponse.acceptedAt,
            releaseResponse.name,
            project.name,
            content,
        )
    }

    fun summarize(storiesJson: String): String {
        val chatClient = chatClientBuilder.build()
        return chatClient.prompt()
            .system { s -> s.text(whatsNewPrompt).param("stories", "storiesJson") }
            .call()
            .content()
    }

    fun getWhatsNewForProjectRelease(projectId: Int, releaseId: Int): WhatsNew? =
        whatsNewRepository.findByProjectIdAndReleaseId(projectId, releaseId)
}
