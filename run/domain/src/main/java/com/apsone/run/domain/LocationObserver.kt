package com.apsone.run.domain

import com.apsone.core.domain.location.LocationWithAltitude
import kotlinx.coroutines.flow.Flow

interface LocationObserver {
    fun observeLocation(interval:Long): Flow<LocationWithAltitude>
}