package com.ahmedmq.whatsnew.release.summary.api

import com.ahmedmq.whatsnew.release.summary.aWhatsNew
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

@WebMvcTest(WhatsNewUIController::class)
class WhatsNewUIControllerTest {

    @MockkBean
    lateinit var mockWhatsNewService: WhatsNewService

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `return whats new data`() {
        every {
            mockWhatsNewService.getWhatsNewForProject(
                WhatsNewRequest(
                    "token",
                    1,
                    "apiKey",
                ),
            )
        } returns listOf(
            aWhatsNew(),
        )
        mockMvc.perform(
            post("/whats-new")
                .contentType(MediaType.APPLICATION_JSON)
                .param("apiToken", "token")
                .param("projectId", "1")
                .param("openAiToken", "apiKey"),
        )
            .andExpect(status().isOk)
            .andExpect(view().name("index"))
//            .andExpect(MockMvcResultMatchers.model().attributeExists("whatsNewResponse"))
    }

    @Test
    fun `return whats new data for release id for given project id`() {
        every { mockWhatsNewService.getWhatsNewForProjectRelease(1, 1) } returns aWhatsNew()
        mockMvc.perform(
            get("/whats-new/1/1"),
        )
            .andExpect(status().isOk)
            .andExpect(view().name("index"))
    }
}
