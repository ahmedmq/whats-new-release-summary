package com.ahmedmq.whatsnew.release.summary.e2e

import com.ahmedmq.whatsnew.release.summary.e2e.pages.WhatsNewPage
import com.microsoft.playwright.*
import com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat
import org.junit.jupiter.api.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WhatsNewE2ETest {

    @LocalServerPort
    val port: Int = 0

    lateinit var context: BrowserContext
    lateinit var page: Page

    @BeforeEach
    fun setUp() {
        context = browser.newContext(
            Browser.NewContextOptions()
                .setBaseURL("http://localhost:$port"),
        )
        page = context.newPage()
    }

    @AfterEach
    fun tearDown() {
        context.close()
    }

    @Test
    fun `should navigate to whats new page`() {
        val whatsNewPage = WhatsNewPage(page)

        whatsNewPage.open()

        with(page) {
            assertThat(getByText("🔥🔥🔥🔥")).isVisible()
            assertThat(getByText("Fresh Out the Oven")).isVisible()
            assertThat(getByText("See the latest feature releases, product improvements and bug fixes from the {{Placeholder team}}")).isVisible()
        }
    }

    @Test
    fun `toggle navbar`() {
        val whatsNewPage = WhatsNewPage(page)
        val header = whatsNewPage.Header()

        whatsNewPage.open()

        assertThat(page.getByText("About")).not().isVisible()
        header.hamburger().click()
        assertThat(page.getByText("About")).isVisible()
    }

    companion object {
        private lateinit var playwright: Playwright
        private lateinit var browser: Browser

        @JvmStatic
        @BeforeAll
        fun launchBrowser() {
            playwright = Playwright.create()
            browser = playwright.chromium()
                .launch()
        }

        @JvmStatic
        @AfterAll
        fun closeBrowser() {
            playwright.close()
        }
    }
}
