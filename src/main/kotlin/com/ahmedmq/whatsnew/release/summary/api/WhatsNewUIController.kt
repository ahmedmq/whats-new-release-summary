package com.ahmedmq.whatsnew.release.summary.api

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class WhatsNewUIController(val whatsNewService: WhatsNewService) {

    @GetMapping("/")
    fun index(model: Model): String {
        model.addAttribute(
            "whatsNewRequest",
            WhatsNewRequest(
                "",
                1,
                "",
            ),
        )
        return "index"
    }

    @PostMapping("/whats-new")
    fun whatsNew(whatsNewRequest: WhatsNewRequest, model: Model): String {
        val whatsNewList = whatsNewService.getWhatsNewForProject(whatsNewRequest)
        model.addAttribute("whatsNewList", whatsNewList)
        return "index"
    }

    @GetMapping("/whats-new/{projectId}/{releaseId}")
    fun getRelease(@PathVariable projectId: Int, @PathVariable releaseId: Int, model: Model): String {
        model.addAttribute("whatsNewRequest", WhatsNewRequest("", 1, ""))
        whatsNewService.getWhatsNewForProjectRelease(projectId, releaseId)
        return "index"
    }
}
