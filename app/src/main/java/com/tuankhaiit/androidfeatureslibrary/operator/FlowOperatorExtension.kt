package com.tuankhaiit.androidfeatureslibrary.operator

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <T> Flow<T>.dropUntilTimeout(durationMillis: Long): Flow<T> = flow {
    var lastTime = System.currentTimeMillis()
    collect {
        if (System.currentTimeMillis() >= lastTime) {
            lastTime = System.currentTimeMillis() + durationMillis
            emit(it)
        }
    }
}