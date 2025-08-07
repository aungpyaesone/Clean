package com.apsone.run.presentation.run_overview.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.apsone.core.domain.run.Run
import com.apsone.core.presentation.ui.formatted
import com.apsone.core.presentation.ui.toFormattedKm
import com.apsone.core.presentation.ui.toFormattedKmh
import com.apsone.core.presentation.ui.toFormattedMeters
import com.apsone.core.presentation.ui.toFormattedPace
import com.apsone.run.presentation.run_overview.RunUi
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun Run.toRunUi(): RunUi{
    val dateTimeInLocalTime = dateTimeUtc
        .withZoneSameInstant(ZoneId.systemDefault())

    val formattedDateTime = DateTimeFormatter
        .ofPattern("MMM dd, yyyy - hh:mma")
        .format(dateTimeInLocalTime)

    val distanceKm = distanceMeters / 1000.0
    
    return RunUi(
        id = id,
        duration = duration.formatted(),
        dateTime = formattedDateTime,
        distance = distanceKm.toFormattedKm(),
        avgSpeed = avgSpeedKmh.toFormattedKmh(),
        maxSpeed = maxSpeedKmh.toFormattedKmh(),
        pace = duration.toFormattedPace(distanceKm),
        totalElevation = totalElevationMeters.toFormattedMeters(),
        mapPictureUrl = mapPictureUrl
    )


}