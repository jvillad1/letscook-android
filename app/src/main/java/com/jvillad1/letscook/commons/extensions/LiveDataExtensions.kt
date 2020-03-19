package com.jvillad1.letscook.commons.extensions

import androidx.lifecycle.*
import com.jvillad1.letscook.commons.base.Output
import kotlinx.coroutines.Dispatchers

/**
 * Extension functions for LiveData class.
 *
 * @author juan.villada
 */

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, body: (T) -> Unit = {}) =
    liveData.observe(this, Observer { it?.let { t -> body(t) } })

fun <T, L> responseLiveData(
    roomQueryToRetrieveData: () -> LiveData<T>,
    networkRequest: suspend () -> Output<L>,
    roomQueryToSaveData: suspend (L) -> Unit
): LiveData<Output<T>> = liveData(Dispatchers.IO) {
    emit(Output.loading(null))
    val source = roomQueryToRetrieveData().map { Output.success(it) }
    emitSource(source)

    when (val responseStatus = networkRequest()) {
        is Output.Success -> {
            roomQueryToSaveData(responseStatus.data)
        }

        is Output.Error -> {
            emit(Output.error(responseStatus.message))
            emitSource(source)
        }

        else -> {
            emit(Output.error("Something went wrong, please try again later"))
            emitSource(source)
        }
    }
}
