package com.tuankhaiit.androidfeatureslibrary.domain.common

sealed class DataState<T> {
    data class Result<T>(val data: T?) : DataState<T>()
    data class Error<T>(val throwable: Throwable) : DataState<T>()
}

fun <T> DataState<T>.onResult(onResult: (data: T?) -> Unit): DataState<T> {
    if (this is DataState.Result) {
        onResult.invoke(this.data)
    }
    return this
}

fun <T> DataState<T>.onError(onError: (e: Throwable) -> Unit): DataState<T> {
    if (this is DataState.Error) {
        onError.invoke(this.throwable)
    }
    return this
}

fun <T> DataState<T>.onEach(
    onResult: (data: T?) -> Unit,
    onError: (e: Throwable) -> Unit
): DataState<T> {
    this.onError(onError)
    this.onResult(onResult)
    return this
}

fun <In, Out> DataState<In>.map(
    mapResult: (data: In?) -> Out,
    mapError: (e: Throwable) -> Out
): Out {
    return when (this) {
        is DataState.Result -> mapResult.invoke(data)
        is DataState.Error -> mapError.invoke(throwable)
    }
}