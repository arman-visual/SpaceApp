package com.avisual.spaceapp.ui

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher


internal class DrawableMatcher(
    private val expectedId: Int,
    private val tintColorId: Int? = null
) : TypeSafeMatcher<View>(View::class.java) {

    /**
     * If tint color is undefined, the drawables will be tinted black before comparison.
     */

    private var resourceName: String? = null
    override fun matchesSafely(item: View): Boolean {
        val itemDrawable = when (item) {
            is ImageView -> when (expectedId) {
                EMPTY -> return item.drawable == null
                ANY -> return item.drawable != null
                else -> item.drawable.let {
                    if (it is StateListDrawable)
                        it.current
                    else
                        it
                }
            }
            is MaterialButton -> when (expectedId) {
                EMPTY -> return item.icon == null
                ANY -> return item.icon != null
                else -> item.icon.let {
                    if (it is StateListDrawable)
                        it.current
                    else
                        it
                }
            }
            else -> return false
        }.mutate().constantState?.newDrawable()?.mutate() ?: return false // clones drawable
        val resources: Resources = item.context.resources
        val expectedDrawable = ContextCompat.getDrawable(item.context, expectedId) ?: return false
        // Apply tint
        if (tintColorId == null) {
            itemDrawable.setTint(resources.getColor(android.R.color.black, item.context.theme))
            expectedDrawable.setTint(resources.getColor(android.R.color.black, item.context.theme))
        } else {
            expectedDrawable.setTint(resources.getColor(tintColorId, item.context.theme))
        }
        // Compare
        resourceName = resources.getResourceEntryName(expectedId)
        val bitmap = getBitmap(itemDrawable)
        val otherBitmap = getBitmap(expectedDrawable)
        return bitmap.sameAs(otherBitmap)
    }

    private fun getBitmap(drawable: Drawable): Bitmap {
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    override fun describeTo(description: Description) {
        description.appendText("with drawable from resource id: ")
        description.appendValue(expectedId)
        if (resourceName != null) {
            description.appendText("[")
            description.appendText(resourceName)
            description.appendText("]")
        }
    }

    companion object {
        const val EMPTY = -1
        const val ANY = -2
    }
}
