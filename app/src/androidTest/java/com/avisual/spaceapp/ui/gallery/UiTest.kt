package com.avisual.spaceapp.ui.gallery

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.avisual.spaceapp.R
import com.avisual.spaceapp.data.server.NasaGalleryClient
import com.avisual.spaceapp.ui.EspressoTestMatchers
import com.avisual.spaceapp.ui.MockWebServerRule
import com.avisual.spaceapp.ui.OkHttp3IdlingResource
import com.avisual.spaceapp.ui.fromJson
import okhttp3.mockwebserver.MockResponse
import org.junit.*
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.koin.test.KoinTest
import org.koin.test.get
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class UiTest : KoinTest {

    private val mockWebServerRule = MockWebServerRule()

    private lateinit var resource:OkHttp3IdlingResource

    @get:Rule
    val testRule: RuleChain = RuleChain
        .outerRule(mockWebServerRule)
        .around(ActivityScenarioRule(GalleryActivity::class.java))

    @Before
    fun setUp() {
        mockWebServerRule.server.enqueue(
            MockResponse().fromJson("nasalibraryresponse.json")
        )

        resource = OkHttp3IdlingResource.create("OkHttp", get<NasaGalleryClient>().clientSetup)
        IdlingRegistry.getInstance().register(resource)
    }

    @Test
    fun test_1_click_a_photo_and_stored_photo_in_favorite() {
        onView(withId(R.id.recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                4,
                click()
            )
        )

        onView(withId(R.id.title_photo_detail))
            .check(matches(withText("Mars Curiosity Wheel Mock-up")))

        onView(withId(R.id.description_photo_detail))
            .check(matches(withText("Mars Curiosity Wheel Mock-up")))

        onView(withId(R.id.image_photo))
            .check(matches(isDisplayed()))

        onView(withId(R.id.fbt_save_favorite)).check(matches(EspressoTestMatchers.withDrawable(R.drawable.photo_no_saved)))
            .perform(
                click()
            )

        onView(withId(R.id.fbt_download)).check(matches(isDisplayed())).perform(pressBack())

    }

    @Test
    fun test_2_navigate_to_stored_gallery_photo_and_navigate_to_detail() {
        onView(withId(R.id.savedPhotosFragment)).perform(click())
        onView(withText("Mars Curiosity Wheel Mock-up")).perform(
            click()
        )
        onView(withId(R.id.description_photo_detail))
            .check(matches(withText("Mars Curiosity Wheel Mock-up")))

        onView(withId(R.id.title_photo_detail))
            .check(matches(withText("Mars Curiosity Wheel Mock-up")))
            .perform(pressBack())
    }
    @Test
    fun test_3_navigate_to_stored_gallery_photo_and_delete_with_swipe_from_database() {
        onView(withId(R.id.savedPhotosFragment)).perform(click())
        onView(withId(R.id.recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                swipeLeft()
            )
        )
    }

    @After
    fun tearDown() {
        //IdlingRegistry.getInstance().unregister(resource)
    }
}