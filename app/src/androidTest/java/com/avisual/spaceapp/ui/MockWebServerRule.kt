package com.avisual.spaceapp.ui

import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlin.concurrent.thread

class MockWebServerRule : TestRule {
    val server = MockWebServer()

    override fun apply(base: Statement, description: Description): Statement =
        object : Statement() {
            override fun evaluate() {
                server.start(8080)
                replaceBaseUrl()
                base.evaluate()
                server.shutdown()
            }

        }

    private fun replaceBaseUrl() {
        val testModule = module(override = true) {
            single(named("baseUrlApiNasa")) { askMockServerUrlOnAnotherThread() }
            single(named("baseUrlNasaImages")) { askMockServerUrlOnAnotherThread() }
        }
        loadKoinModules(testModule)
    }

    private fun askMockServerUrlOnAnotherThread(): String {
        /*
        This needs to be done immediately, but the App will crash with
        "NetworkOnMainThreadException" if this is not extracted from the main thread. So this is
        a "hack" to prevent it. We don't care about blocking in a test, and it's fast.
        */
        var url = ""
        val t = thread {
            url = server.url("/").toString()
        }
        t.join()
        return url
    }
}