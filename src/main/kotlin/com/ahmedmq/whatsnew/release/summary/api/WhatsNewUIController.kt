package com.ahmedmq.whatsnew.release.summary.api

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class WhatsNewUIController {

    @PostMapping("/whats-new")
    fun whatsNew(whatsNewRequest: WhatsNewRequest, model: Model): String = "index"

    @GetMapping("/whats-new/{releaseId}")
    fun getRelease(@PathVariable releaseId: String): String {
        return "release"
    }
}