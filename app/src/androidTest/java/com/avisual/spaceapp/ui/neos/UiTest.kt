package com.avisual.spaceapp.ui.neos

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.GeneralSwipeAction
import androidx.test.espresso.action.Swipe
import androidx.test.espresso.action.ViewActions.*
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
import com.avisual.spaceapp.ui.asteroidsNeo.AsteroidsNeoActivity
import com.avisual.spaceapp.ui.fromJson
import okhttp3.mockwebserver.MockResponse
import org.hamcrest.Matcher
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
        .around(ActivityScenarioRule(AsteroidsNeoActivity::class.java))

    @Before
    fun setUp() {
        mockWebServerRule.server.enqueue(
            MockResponse().fromJson("neosonlydateresponse.json")
        )

        val resource = OkHttp3IdlingResource.create("OkHttp", get<NasaClient>().clientSetup)
        IdlingRegistry.getInstance().register(resource)
    }

    @Test
    fun search_neos_by_only_date() {
        onView(withId(R.id.show_input)).perform(click())
        onView(withText("ACEPTAR")).perform(click())
        onView(withId(R.id.search)).perform(click())
        onView(withId(R.id.recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                4,
                click()
            )
        )

        onView(withId(R.id.fbt_save_favorite))
            .perform(click())

        onView(withId(R.id.name_neo))
            .check(ViewAssertions.matches(withText("(2019 TK5)")))

        onView(withId(R.id.minDiameter))
            .check(ViewAssertions.matches(withText("0.0088014652")))

        onView(withId(R.id.maxDiameter))
            .check(ViewAssertions.matches(withText("0.0196806745")))

        onView(withId(R.id.relativeVelocity))
            .check(ViewAssertions.matches(withText("17273.2807133695")))

        onView(withId(R.id.absoulteMagnitud))
            .check(ViewAssertions.matches(withText("27.4")))

        onView(withId(R.id.approachDate))
            .check(ViewAssertions.matches(withText("2022-Jun-24 00:17")))

        onView(withId(R.id.missDistance))
            .check(ViewAssertions.matches(withText("28344486.89915891")))
            .perform(pressBack())
    }

    @Test
    fun navigate_to_stored_neos() {
        onView(withId(R.id.storedNeoFragment)).perform(click())

        onView(withText("(2019 TK5)")).perform(click())

        onView(withId(R.id.name_neo))
            .check(ViewAssertions.matches(withText("(2019 TK5)")))

        onView(withId(R.id.minDiameter))
            .check(ViewAssertions.matches(withText("0.0088014652")))

        onView(withId(R.id.maxDiameter))
            .check(ViewAssertions.matches(withText("0.0196806745")))

        onView(withId(R.id.relativeVelocity))
            .check(ViewAssertions.matches(withText("17273.2807133695")))

        onView(withId(R.id.absoulteMagnitud))
            .check(ViewAssertions.matches(withText("27.4")))

        onView(withId(R.id.approachDate))
            .check(ViewAssertions.matches(withText("2022-Jun-24 00:17")))

        onView(withId(R.id.missDistance))
            .check(ViewAssertions.matches(withText("28344486.89915891")))
    }
}
