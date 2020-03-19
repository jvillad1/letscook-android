package com.jvillad1.letscook.commons.extensions

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout

/**
 * Extension functions for Views.
 *
 * @author juan.villada
 */

/**
 * Extension function to set a [View] visible.
 */
fun View.visible() {
    visibility = View.VISIBLE
}

/**
 * Extension function to set a [View] gone.
 */
fun View.gone() {
    visibility = View.GONE
}

/**
 * Extension function to set a [View] invisible.
 */
fun View.invisible() {
    visibility = View.INVISIBLE
}

internal fun View?.findSuitableParent(): ViewGroup? {
    var view = this
    var fallback: ViewGroup? = null
    do {
        if (view is CoordinatorLayout) {
            // We've found a CoordinatorLayout, use it
            return view
        } else if (view is FrameLayout) {
            if (view.id == android.R.id.content) {
                // If we've hit the decor content view, then we didn't find a CoL in the
                // hierarchy, so use it.
                return view
            } else {
                // It's not the content view but we'll use it as our fallback
                fallback = view
            }
        }

        if (view != null) {
            // Else, we will loop and crawl up the view hierarchy and try to find a parent
            val parent = view.parent
            view = if (parent is View) parent else null
        }
    } while (view != null)

    // If we reach here then we didn't find a CoL or a suitable content view so we'll fallback
    return fallback
}
