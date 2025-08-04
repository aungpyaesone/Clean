package com.apsone.run.location

import android.location.Location
import com.apsone.core.domain.location.LocationWithAltitude

fun Location.toLocationWithAttitude(): LocationWithAltitude {
    return LocationWithAltitude(
        location = com.apsone.core.domain.location.Location(
            lat = latitude,
            long = longitude
        ),
        altitude = altitude
    )
}