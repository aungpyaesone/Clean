package com.apsone.core.domain.location

import kotlin.time.Duration

data class LocationWithTimeStamp(
    val locationWithAltitude: LocationWithAltitude,
    val durationTimeStamp: Duration
)