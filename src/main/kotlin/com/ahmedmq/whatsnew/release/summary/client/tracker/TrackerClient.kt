package com.ahmedmq.whatsnew.release.summary.client.tracker

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange
import java.time.Instant

interface TrackerClient {

    @GetExchange("/projects/{projectId}")
    fun project(
        @RequestHeader("X-TrackerToken") apiToken: String,
        @PathVariable projectId: Int,
    ): ProjectResponse

    @GetExchange("/projects/{projectId}/releases")
    fun releases(
        @RequestHeader("X-TrackerToken") apiToken: String,
        @PathVariable projectId: Int,
        @RequestParam params: Map<String, String>,
    ): List<ReleaseResponse>

    @GetExchange("/projects/{projectId}/releases/{releaseId}/stories")
    fun releaseStories(
        @RequestHeader("X-TrackerToken") apiToken: String,
        @PathVariable projectId: Int,
        @PathVariable releaseId: Int,
        @RequestParam params: Map<String, String>,
    ): List<StoryResponse>
}

data class ProjectResponse(
    val id: Int,
    val name: String,
)

data class ReleaseResponse(
    val id: Int,
    val name: String,
    @JsonProperty("accepted_at") val acceptedAt: Instant,
)

data class StoryResponse(
    val id: Int,
    val name: String,
    val description: String,
    @JsonProperty("story_type") val storyType: String,
    val labels: List<LabelsResponse>,
)

data class LabelsResponse(
    val id: Int,
    val name: String,
)
