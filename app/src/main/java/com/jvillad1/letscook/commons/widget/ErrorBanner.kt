package com.jvillad1.letscook.commons.widget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.jvillad1.letscook.commons.widget.ErrorBannerView.ErrorBannerListener
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.jvillad1.letscook.R
import com.jvillad1.letscook.commons.extensions.findSuitableParent

/**
 * Class for the creating an [ErrorBanner] with the [ErrorBannerView].
 *
 * @author juan.villada
 */
class ErrorBanner(
    parent: ViewGroup,
    content: ErrorBannerView
) : BaseTransientBottomBar<ErrorBanner>(parent, content, content) {

    init {
        getView().setBackgroundColor(ContextCompat.getColor(view.context, android.R.color.transparent))
        getView().setPadding(0, 0, 0, 0)
    }

    companion object {

        fun make(
            view: View,
            @StringRes titleResId: Int,
            @StringRes messageResId: Int,
            withRetry: Boolean = false,
            withDismiss: Boolean = true,
            errorBannerListener: ErrorBannerListener
        ): ErrorBanner {

            // First we find a suitable parent for our custom view
            val parent = view.findSuitableParent() ?: throw IllegalArgumentException(
                "No suitable parent found from the given view. Please provide a valid view."
            )

            // We inflate our custom view
            val customView = LayoutInflater.from(view.context).inflate(
                R.layout.layout_error_banner,
                parent,
                false
            ) as ErrorBannerView

            with(customView) {
                setTitle(titleResId)
                setMessage(messageResId)
                toggleRetryVisibility(withRetry)
                toggleDismissVisibility(withDismiss)
                setListener(errorBannerListener)
            }

            // We create and return our Snackbar
            return ErrorBanner(
                parent,
                customView
            ).setDuration(LENGTH_INDEFINITE)
        }
    }
}
