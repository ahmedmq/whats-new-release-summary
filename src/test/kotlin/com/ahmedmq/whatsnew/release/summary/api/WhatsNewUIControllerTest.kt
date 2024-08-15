package com.ahmedmq.whatsnew.release.summary.api

import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@WebMvcTest
class WhatsNewUIControllerTest {

    val mockMvc: MockMvc = MockMvcBuilders
        .standaloneSetup(WhatsNewUIController::class.java)
        .build()

    @Test
    fun `return whats-new data`() {
        mockMvc.perform(
            post("/whats-new")
                .contentType(MediaType.APPLICATION_JSON)
                .param("apiToken", "XXX")
                .param("projectId","123")
            )
            .andExpect(status().isOk)
            .andExpect(view().name("index"))
//            .andExpect(MockMvcResultMatchers.model().attributeExists("whatsNewResponse"))
    }
}