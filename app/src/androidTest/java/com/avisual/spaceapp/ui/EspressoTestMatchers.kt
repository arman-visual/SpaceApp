package com.avisual.spaceapp.ui

import android.view.View
import org.hamcrest.Matcher

class EspressoTestMatchers {

    companion object{

        fun withDrawable(resourceId: Int): Matcher<View> {
            return DrawableMatcher(resourceId)
        }

        fun noDrawable(): Matcher<View> {
            return DrawableMatcher(-1)
        }


        fun hasDrawable(): Matcher<View> {
            return DrawableMatcher(DrawableMatcher.ANY)
        }
    }
}