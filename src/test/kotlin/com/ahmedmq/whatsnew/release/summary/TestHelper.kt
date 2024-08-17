package com.ahmedmq.whatsnew.release.summary

import com.ahmedmq.whatsnew.release.summary.persistence.WhatsNew
import java.time.Instant

fun aWhatsNew(
    releaseId: Int = 1,
    projectId: Int = 1,
    acceptedAt: Instant = Instant.parse("2024-01-01T01:01:01.00Z"),
    name: String = "Test Release",
    projectName: String = "Project X",
    content: String = "GenAI",
): WhatsNew =
    WhatsNew(
        releaseId,
        projectId,
        acceptedAt,
        name,
        projectName,
        content,
    )
