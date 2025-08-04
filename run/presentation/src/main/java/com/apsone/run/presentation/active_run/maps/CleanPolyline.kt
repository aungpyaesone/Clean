package com.apsone.run.presentation.active_run.maps

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.apsone.core.domain.location.LocationWithTimeStamp
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Polyline

@Composable
fun CleanPolyLines(
    locations:List<List<LocationWithTimeStamp>>
) {
    val polyLines = remember(locations) {
        locations.map {
            it.zipWithNext{ t1,t2 ->
                PolylineUi(
                    location1 = t1.locationWithAltitude.location,
                    location2 = t2.locationWithAltitude.location,
                    color = PolyLineColorCalculator.locationToColor(
                        t1,t2
                    )
                )

            }
        }
    }
    polyLines.forEach { polyLine ->
        polyLine.forEach { polyLineUi ->
            Polyline(
                points = listOf(
                    LatLng(polyLineUi.location1.lat,polyLineUi.location1.long),
                    LatLng(polyLineUi.location2.lat,polyLineUi.location2.long)
                ),
                color = polyLineUi.color,
                jointType = JointType.BEVEL
            )
        }
    }
    
}