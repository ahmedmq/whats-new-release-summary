package com.ahmedmq.whatsnew.release.summary.api

import com.ahmedmq.whatsnew.release.summary.client.TrackerClient
import com.ahmedmq.whatsnew.release.summary.persistence.WhatsNew
import com.ahmedmq.whatsnew.release.summary.persistence.WhatsNewRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class WhatsNewService(val whatsNewRepository: WhatsNewRepository, val trackerClient: TrackerClient) {

    @Value("\${classpath:/prompts/whats-new-release}")
    lateinit var whatsNewPrompt: Resource

    fun getWhatsNewForProject(whatsNewRequest: WhatsNewRequest): List<WhatsNew> {
        val releases = trackerClient.releases(
            whatsNewRequest.apiToken, whatsNewRequest.projectId, mapOf(
                "fields" to "id,name,accepted_at",
                "with_state" to "accepted_at"
            )
        )

        return releases.reversed().mapIndexed { index, r ->
            if (index == 0) {
                whatsNewRepository.findByProjectIdAndReleaseId(
                    whatsNewRequest.projectId,
                    releases.last().id
                ) ?: TODO()

            } else {
                WhatsNew(
                    r.id,
                    whatsNewRequest.projectId,
                    r.acceptedAt,
                    "",
                    ""
                )
            }

        }
    }


    fun getWhatsNewForProjectRelease(projectId: Int, releaseId: Int): WhatsNew? =
        whatsNewRepository.findByProjectIdAndReleaseId(projectId, releaseId)


}