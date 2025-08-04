@file:OptIn(ExperimentalCoroutinesApi::class)

package com.apsone.run.domain

import com.apsone.core.domain.location.LocationWithTimeStamp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlin.math.roundToInt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class RunningTracker(
    private val locationObserver: LocationObserver,
    private val applicationScope: CoroutineScope
) {
    private val _runData = MutableStateFlow(RunData())
    val runData = _runData.asStateFlow()

    private val _isTracking = MutableStateFlow(false)
    val isTracking = _isTracking.asStateFlow()

    private val _elapsedTime = MutableStateFlow(Duration.ZERO)
    val elapsedTime = _elapsedTime.asStateFlow()

    private val isObservingLocation = MutableStateFlow(false)
    val currentLocation = isObservingLocation
        .flatMapLatest { isObservingLocation ->
            if(isObservingLocation){
                locationObserver.observeLocation(1000L)
            }else{
                flowOf()
            }
        }.stateIn(
            applicationScope,
            SharingStarted.Lazily,
            null
        )

    init {
        _isTracking
            .onEach { isTracking ->
                if(!isTracking){
                    val newList = buildList {
                        addAll(runData.value.locations)
                        add(emptyList())
                    }.toList()
                    _runData.update {
                        it.copy(
                            locations = newList
                        )
                    }
                }
            }
            .flatMapLatest { isTracking ->
                if(isTracking){
                    Timer.timeAndEmit()
                }else{
                    flowOf()
                }
            }.onEach {
                _elapsedTime.value += it
            }.launchIn(applicationScope)

        currentLocation.filterNotNull()
            .combineTransform(_isTracking) { location, isTracking ->
                if(isTracking){
                    emit(location)
                }
            }.zip(_elapsedTime) { location, elapsedTime ->
                LocationWithTimeStamp(
                    locationWithAltitude = location,
                    durationTimeStamp = elapsedTime
                )
            }.onEach { location ->
                val currentLocation = runData.value.locations
                val lastLocationList = if(currentLocation.isNotEmpty()){
                    currentLocation.last() + location
                }else {
                    listOf(location)
                }
                val newLocationList = currentLocation.replaceList(lastLocationList)
                val distanceMeter = LocationDataCalculator.getTotalDistanceMeters(newLocationList)
                val distanceKm = distanceMeter / 1000.0
                val currentDuration = location.durationTimeStamp
                val avgSecondsPerKm = if(distanceKm == 0.0) 0 else
                    (currentDuration.inWholeSeconds / distanceKm).roundToInt()
                _runData.update {
                    RunData(
                        distanceMeters = distanceMeter,
                        pace = avgSecondsPerKm.seconds,
                        locations = newLocationList
                    )
                }
            }.launchIn(applicationScope)
    }

    fun setIsTracking(isTracking: Boolean){
        _isTracking.value = isTracking
    }

    fun startObserving(){
        isObservingLocation.value = true
    }

    fun stopObserving(){
        isObservingLocation.value = false
    }

    private fun <T> List<List<T>>.replaceList(replaceList:List<T>):List<List<T>>{
        if(this.isEmpty()){
            return listOf(replaceList)
        }
        return this.dropLast(1) + listOf(replaceList)
    }
}