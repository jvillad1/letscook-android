package com.jvillad1.letscook.commons

import retrofit2.Response

/**
 * Abstract class that simplifies the error handling with retrofit requests.
 * @param <T>
 *
 * @author juan.villada
 */
abstract class BaseRemoteDataSource {

    protected suspend fun <T> getData(call: suspend () -> Response<T>): Output<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Output.success(body)
            }
            return formatError(" ${response.code()} ${response.message()}")
        } catch (exception: Exception) {
            return formatError(exception.message.toString())
        }
    }

    private fun <T> formatError(errorMessage: String): Output<T> {
        return Output.error("Network call has failed for the following reason: $errorMessage")
    }
}
