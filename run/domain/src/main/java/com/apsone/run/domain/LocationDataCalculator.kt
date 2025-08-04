package com.apsone.run.domain

import com.apsone.core.domain.location.LocationWithTimeStamp
import kotlin.math.roundToInt

object LocationDataCalculator {
    fun getTotalDistanceMeters(locations: List<List<LocationWithTimeStamp>>): Int {
        return locations
            .sumOf { timestampsPerLine ->
                timestampsPerLine.zipWithNext { location1, location2 ->
                    location1.locationWithAltitude.location.distanceTo(location2.locationWithAltitude.location)
                }.sum().roundToInt()
            }

    }
}