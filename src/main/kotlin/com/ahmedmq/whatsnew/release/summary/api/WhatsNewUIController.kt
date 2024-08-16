package com.ahmedmq.whatsnew.release.summary.api

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class WhatsNewUIController(val whatsNewService: WhatsNewService) {

    @PostMapping("/whats-new")
    fun whatsNew(whatsNewRequest: WhatsNewRequest, model: Model): String {
        whatsNewService.getWhatsNewForProject(whatsNewRequest)
        return "index"
    }

    @GetMapping("/whats-new/{projectId}/{releaseId}")
    fun getRelease(@PathVariable projectId: Int, @PathVariable releaseId: Int, model: Model): String {
        whatsNewService.getWhatsNewForProjectRelease(projectId, releaseId)
        return "index"
    }
}