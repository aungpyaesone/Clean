package com.apsone.core.presentation.ui

import kotlin.math.pow
import kotlin.math.round
import kotlin.math.roundToInt
import kotlin.time.Duration

fun Duration.formatted(): String{
    val totalSeconds = inWholeSeconds
    val hours = String.format("%02d", totalSeconds / (60 * 60))
    val minutes = String.format("%02d",(totalSeconds%3600)/60)
    val second = String.format("%02d", (totalSeconds%60))
    return "$hours:$minutes:$second"
}

fun Double.toFormattedKm(): String{
    return "${this.roundToDecimals(1)} km"
}

fun Double.toFormattedKmh(): String{
    return "${roundToDecimals(1)} km/h"
}

fun Int.toFormattedMeters(): String{
    return "$this m"
}

fun Duration.toFormattedPace(distanceKm: Double): String{
    if(this == Duration.ZERO || distanceKm < 0.0){
        return "-"
    }
    val secondsPerKm = (this.inWholeSeconds/distanceKm).roundToInt()
    val averagePaceMinutes = secondsPerKm / 60
    val averagePaceSeconds = String.format("%2d", secondsPerKm % 60)
    return "$averagePaceMinutes:$averagePaceSeconds / km"
}

private fun Double.roundToDecimals(decimals: Int): Double{
    val factor = 10f.pow(decimals)
    return round(this * factor)/factor
}