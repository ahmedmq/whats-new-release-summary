package com.ahmedmq.whatsnew.release.summary.e2e.pages

import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import com.microsoft.playwright.options.AriaRole

class WhatsNewPage(val page: Page) {

    fun open() {
        page.navigate("/")
    }

    inner class Header() {
        private var locator: Locator = page.locator("header")

        fun title(): Locator = locator.getByRole(AriaRole.LINK)

        fun hamburger(): Locator = locator.getByRole(AriaRole.BUTTON)
    }
}
