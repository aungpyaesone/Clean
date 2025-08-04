package com.apsone.run.presentation.active_run

import com.apsone.core.domain.location.Location
import com.apsone.run.domain.RunData
import kotlin.time.Duration

data class ActiveRunState(
    val elapsedTime : Duration = Duration.ZERO,
    val runData: RunData = RunData(),
    val shouldTrack: Boolean = false,
    val hasStartRunning: Boolean = false,
    val location: Location? = null,
    val isRunFinished: Boolean = false,
    val isSavingRun: Boolean = false,
    val showLocationRationale: Boolean = false,
    val showNotificationRationale: Boolean = false
)