package com.avisual.spaceapp.ui.rover

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.avisual.spaceapp.R
import com.avisual.spaceapp.data.server.NasaClient
import com.avisual.spaceapp.ui.MockWebServerRule
import com.avisual.spaceapp.ui.OkHttp3IdlingResource
import com.avisual.spaceapp.ui.fromJson
import com.avisual.spaceapp.ui.roverMars.NavRoverMarsActivity
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.koin.test.KoinTest
import org.koin.test.get

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class UiTest : KoinTest {

    private val mockWebServerRule = MockWebServerRule()

    @get:Rule
    val testRule: RuleChain = RuleChain
        .outerRule(mockWebServerRule)
        .around(ActivityScenarioRule(NavRoverMarsActivity::class.java))

    @Before
    fun setUp() {
        mockWebServerRule.server.enqueue(
            MockResponse().fromJson("roverresponse.json")
        )

        val resource = OkHttp3IdlingResource.create("OkHttp", get<NasaClient>().clientSetup)
        IdlingRegistry.getInstance().register(resource)
    }

    @Test
    fun test1_search_and_navigate_to_detail() {
        onView(withId(R.id.show_input)).perform(click())
        onView(withText("ACEPTAR")).perform(click())
        onView(withId(R.id.search)).perform(click())
        onView(withId(R.id.recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                4,
                click()
            )
        )

        onView(withId(R.id.rover_name))
            .check(ViewAssertions.matches(withText("Curiosity")))

        onView(withId(R.id.rover_status))
            .check(ViewAssertions.matches(withText("active")))

        onView(withId(R.id.earth_date))
            .check(ViewAssertions.matches(withText("2020-06-24")))

        onView(withId(R.id.launch_date))
            .check(ViewAssertions.matches(withText("2011-11-26")))

        onView(withId(R.id.launding_date))
            .check(ViewAssertions.matches(withText("2012-08-06")))
    }

}