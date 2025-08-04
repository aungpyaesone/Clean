package com.apsone.run.presentation.active_run.maps

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils
import com.apsone.core.domain.location.LocationWithTimeStamp
import kotlin.math.abs

object PolyLineColorCalculator {

    fun locationToColor(location1: LocationWithTimeStamp, location2: LocationWithTimeStamp): Color{
        val distanceMeters = location1.locationWithAltitude.location.distanceTo(location2.locationWithAltitude.location)
        val timeDiff = abs((location2.durationTimeStamp - location1.durationTimeStamp).inWholeSeconds)
        val speedKmh = (distanceMeters/timeDiff) * 3.6
        return interpolateColor(
            speedKmh = speedKmh,
            minSpeedKmh = 5.0,
            maxSpeedKmh = 20.0,
            colorStart = Color.Green,
            colorMid = Color.Yellow,
            colorEnd = Color.Red
        )
    }

    private fun interpolateColor(
        speedKmh: Double,
        minSpeedKmh: Double,
        maxSpeedKmh: Double,
        colorStart: Color,
        colorMid: Color,
        colorEnd: Color
    ) : Color{
        val ratio = ((speedKmh - minSpeedKmh) / (maxSpeedKmh - minSpeedKmh)).coerceIn(0.0..1.0)
        val colorInt = if(ratio <= 0.5){
             val midRatio = ratio / 0.5
            ColorUtils.blendARGB(colorStart.toArgb(),colorMid.toArgb(),midRatio.toFloat())
        }else{
            val midRatio = (ratio - 0.5) / 0.5
            ColorUtils.blendARGB(colorMid.toArgb(),colorEnd.toArgb(),midRatio.toFloat())
        }
        return Color(colorInt)
    }
}