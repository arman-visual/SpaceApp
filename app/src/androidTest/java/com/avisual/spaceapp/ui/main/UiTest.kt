package com.avisual.spaceapp.ui.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.avisual.spaceapp.R
import com.avisual.spaceapp.ui.mainMenu.MainActivity
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.koin.test.KoinTest

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class UiTest : KoinTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test1_check_menu_buttons_and_navigation() {

        onView(withId(R.id.bt_neows))
            .check(matches((withText("Near Asteroids"))))

        onView(withId(R.id.bt_searchrover))
            .check(matches((withText("photos mars rover"))))

        onView(withId(R.id.bt_nasagallery))
            .check(matches((withText("images in nasa gallery"))))

        onView(withId(R.id.title_main))
            .check(matches((withText("What do you want to search?"))))
    }
}