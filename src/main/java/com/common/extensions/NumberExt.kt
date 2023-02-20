package com.common.extensions

import java.text.DecimalFormat

fun Number.formatMoney(): String {
    return try {
        DecimalFormat("###,##0").format(this)
    } catch (exception: IllegalArgumentException) {
        exception.printStackTrace()
        "0.00"
    }
}

fun Long.centToDollar(): Long = try {
    this / 100
} catch (e: Exception) {
    0L
}

fun Long.dollarToCent(): Long = try {
    this * 100
} catch (e: Exception) {
    0L
}