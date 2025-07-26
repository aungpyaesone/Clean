package com.apsone.run.domain

import com.apsone.core.domain.location.Location
import com.apsone.core.domain.location.LocationWithTimeStamp
import kotlin.time.Duration

data class RunData(
    val distanceMeters: Int  = 0,
    val pace: Duration = Duration.ZERO,
    val locations: List<List<LocationWithTimeStamp>> = emptyList()
)
