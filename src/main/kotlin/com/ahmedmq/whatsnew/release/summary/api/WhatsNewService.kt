package com.ahmedmq.whatsnew.release.summary.api

import com.ahmedmq.whatsnew.release.summary.client.TrackerClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class WhatsNewService(val trackerClient: TrackerClient) {

    @Value("\${tracker.api.token}")
    lateinit var apiToken: String

    @Value("\${classpath:/prompts/whats-new-release}")
    lateinit var whatsNewRelease : Resource


    fun generateWhatsNew() {
        trackerClient.releases(
            apiToken,
            2644785,
            mapOf(
                "fields" to "id,name,current_state,points_accepted,points_total,counts_accepted,counts_total,accepted_at",
                "with_state" to "accepted"
            )
        )
    }

}