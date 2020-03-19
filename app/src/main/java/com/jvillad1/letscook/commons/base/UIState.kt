package com.jvillad1.letscook.commons.base

import androidx.annotation.StringRes
import com.jvillad1.letscook.R

/**
 * Class that holds UI common states.
 *
 * @author juan.villada
 */
sealed class UIState<out T : Any> {
    data class Loading(@StringRes val message: Int = R.string.empty_loading_message) : UIState<Nothing>()
    data class Data<T : Any>(val data: T) : UIState<T>()
    data class Error(@StringRes val message: Int = R.string.general_error_message) : UIState<Nothing>()
}
