package com.developer.revolut.app.util

import kotlin.math.round

fun Double.round(decimals: Int = 2): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}