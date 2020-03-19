package com.jvillad1.letscook.commons.widget

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.ContentViewCallback
import com.jvillad1.letscook.R
import com.jvillad1.letscook.commons.extensions.gone
import com.jvillad1.letscook.commons.extensions.visible
import kotlinx.android.synthetic.main.view_error_banner.view.*

/**
 * Custom view for the [ErrorBanner].
 *
 * @author juan.villada
 */
class ErrorBannerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ContentViewCallback {

    private var errorBannerListener: ErrorBannerListener? = null

    init {
        View.inflate(context, R.layout.view_error_banner, this)
        clipToPadding = false
        initErrorBannerListeners()
    }

    private fun initErrorBannerListeners() {
        retryBannerImageView.setOnClickListener {
            errorBannerListener?.onErrorBannerRetry()
        }

        dismissBannerImageView.setOnClickListener {
            errorBannerListener?.onErrorBannerDismiss()
        }
    }

    fun setTitle(@StringRes titleResId: Int) {
        bannerTitleTextView.text = context.getText(titleResId)
    }

    fun setMessage(@StringRes messageResId: Int) {
        bannerMessageTextView.text = context.getText(messageResId)
    }

    fun toggleRetryVisibility(visible: Boolean) = with(retryBannerImageView) {
        if (visible) {
            visible()
        } else {
            gone()
        }
    }

    fun toggleDismissVisibility(visible: Boolean) = with(dismissBannerImageView) {
        if (visible) {
            visible()
        } else {
            gone()
        }
    }

    fun setListener(errorBannerListener: ErrorBannerListener) {
        this.errorBannerListener = errorBannerListener
    }

    override fun animateContentIn(delay: Int, duration: Int) {
        val scaleX = ObjectAnimator.ofFloat(dismissBannerImageView, View.SCALE_X, 0f, 1f)
        val scaleY = ObjectAnimator.ofFloat(dismissBannerImageView, View.SCALE_Y, 0f, 1f)
        val animatorSet = AnimatorSet().apply {
            interpolator = OvershootInterpolator()
            setDuration(500)
            playTogether(scaleX, scaleY)
        }
        animatorSet.start()
    }

    override fun animateContentOut(delay: Int, duration: Int) {
        // NO-OP
    }

    interface ErrorBannerListener {
        /**
         * To be called when the user press retry in the banner.
         */
        fun onErrorBannerRetry()

        /**
         * To be called when the user closes the banner.
         */
        fun onErrorBannerDismiss()
    }
}
