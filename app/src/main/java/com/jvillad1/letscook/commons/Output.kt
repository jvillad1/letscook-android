package com.jvillad1.letscook.commons

/**
 * A generic class that holds a value with output status.
 * @param <T>
 *
 * @author juan.villada
 */
sealed class Output<out T> {

    data class Success<out T>(val data: T) : Output<T>()
    data class Error<out T>(val message: String) : Output<T>()
    data class Loading<out T>(val value: T? = null) : Output<T>()

    companion object {
        fun <T> success(data: T): Output<T> = Success(data)
        fun <T> error(error_msg: String): Output<T> = Error(error_msg)
        fun <T> loading(value: T?): Output<T> = Loading(value)
    }
}
