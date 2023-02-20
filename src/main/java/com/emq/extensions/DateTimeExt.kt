package com.emq.extensions

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Instant.formatStyleA() =
    toLocalDateTime(TimeZone.currentSystemDefault())
        .run {
            String.format(
                "%d/%02d/%02d %02d:%02d:%02d",
                year,
                monthNumber,
                dayOfMonth,
                hour,
                minute,
                second
            )
        }

fun Instant.formatStyleB() =
    toLocalDateTime(TimeZone.currentSystemDefault())
        .run {
            String.format(
                "%02d-%02d",
                monthNumber,
                dayOfMonth,
            )
        }
