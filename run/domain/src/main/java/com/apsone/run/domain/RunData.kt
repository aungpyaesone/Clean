package com.apsone.run.domain

import com.apsone.core.domain.location.Location
import com.apsone.core.domain.location.LocationWithTimeStamp
import kotlin.time.Duration

data class RunData(
    var distanceMeters: Int  = 0,
    var pace: Duration = Duration.ZERO,
    var locations: List<List<LocationWithTimeStamp>> = emptyList()
)
