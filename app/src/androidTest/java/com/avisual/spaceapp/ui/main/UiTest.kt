package com.avisual.spaceapp.ui.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.avisual.spaceapp.R
import com.avisual.spaceapp.data.server.NasaClient
import com.avisual.spaceapp.ui.MockWebServerRule
import com.avisual.spaceapp.ui.OkHttp3IdlingResource
import com.avisual.spaceapp.ui.fromJson
import com.avisual.spaceapp.ui.mainMenu.MainActivity
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.koin.test.KoinTest
import org.koin.test.get

class UiTest : KoinTest {

    private val mockWebServerRule = MockWebServerRule()

    @get:Rule
    val testRule: RuleChain = RuleChain
        .outerRule(mockWebServerRule)
        .around(ActivityScenarioRule(MainActivity::class.java))

    @Before
    fun setUp() {
        mockWebServerRule.server.enqueue(
            MockResponse().fromJson("nasalibraryresponse.json")
        )

        val resource = OkHttp3IdlingResource.create("OkHttp", get<NasaClient>().clientSetup)
        IdlingRegistry.getInstance().register(resource)
    }

    @Test
    fun clickAButtonNavigatesToGalleryPhotos() {

        onView(withId(R.id.bt_neows))
            .check(matches((withText("Near Asteroids"))))

        onView(withId(R.id.bt_searchrover))
            .check(matches((withText("photos mars rover"))))

        onView(withId(R.id.bt_nasagallery))
            .check(matches((withText("images in nasa gallery"))))

        onView(withId(R.id.bt_nasagallery))
            .perform(click())
    }

}