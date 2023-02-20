package com.common.extensions

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

fun timerMsFlow(
    duration: Long,
    period: Long = 1000L,
    initialDelay: Long = 0
) = flow {
    var currentDuration = duration
    delay(initialDelay)
    currentDuration -= initialDelay
    while (currentDuration > 0) {
        emit(currentDuration)
        delay(period)
        currentDuration -= period
    }
}