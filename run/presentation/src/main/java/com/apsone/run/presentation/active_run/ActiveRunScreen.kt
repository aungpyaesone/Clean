@file:OptIn(ExperimentalMaterial3Api::class)

package com.apsone.run.presentation.active_run

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apsone.core.presentation.designsystem.CleanTheme
import com.apsone.core.presentation.designsystem.R
import com.apsone.core.presentation.designsystem.StartIcon
import com.apsone.core.presentation.designsystem.StopIcon
import com.apsone.core.presentation.designsystem.components.CleanActionButton
import com.apsone.core.presentation.designsystem.components.CleanDialog
import com.apsone.core.presentation.designsystem.components.CleanFloatingAction
import com.apsone.core.presentation.designsystem.components.CleanOutlineActionButton
import com.apsone.core.presentation.designsystem.components.CleanScaffold
import com.apsone.core.presentation.designsystem.components.CleanToolbar
import com.apsone.run.presentation.active_run.components.RunDataCard
import com.apsone.run.presentation.active_run.maps.TrackerMap
import com.apsone.run.presentation.util.hasLocationPermission
import com.apsone.run.presentation.util.hasNotificationPermission
import com.apsone.run.presentation.util.shouldShowLocationPermissionRationale
import com.apsone.run.presentation.util.shouldShowNotificationPermissionRationale
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.HONEYCOMB_MR2)
@Composable
fun ActiveRunScreenRoot(
    onServiceToggle: (Boolean) -> Unit,
    onBackClick: () -> Unit = {},
    viewModel: ActiveRunViewModel = koinViewModel()
) {
    ActiveRunScreen(
        state = viewModel.state,
        onAction = {action ->
            when(action){
                is ActiveRunAction.OnBackClick -> {
                    onBackClick()
                }
                else -> {}
            }
            viewModel.onAction(action)
        },
        onServiceToggle = onServiceToggle
    )

}

@RequiresApi(Build.VERSION_CODES.HONEYCOMB_MR2)
@Composable

private fun ActiveRunScreen(
    state: ActiveRunState,
    onServiceToggle : (isServiceRunning: Boolean) -> Unit = {},
    onAction: (ActiveRunAction) -> Unit
) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) {
        val hasCoarseLocationPermission = it[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        val hasFineLocationPermission = it[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val hasNotificationPermission = if(Build.VERSION.SDK_INT > 33 )it[Manifest.permission.POST_NOTIFICATIONS] == true else true

        val activity = context as ComponentActivity
        val showLocationRationale = activity.shouldShowLocationPermissionRationale()
        val showNotificationRationale = activity.shouldShowNotificationPermissionRationale()

        onAction(ActiveRunAction.SubmitLocationPermissionInfo(
            acceptedLocationPermission = hasCoarseLocationPermission && hasFineLocationPermission,
            shouldShowRationale = showLocationRationale
        ))

        onAction(ActiveRunAction.SubmitNotificationPermissionInfo(
            acceptedNotificationPermission = hasNotificationPermission,
            shouldShowNotificationRationale = showNotificationRationale
        ))
    }

    LaunchedEffect(true) {
        val activity = context as ComponentActivity
        val showLocationRationale = activity.shouldShowLocationPermissionRationale()
        val showNotificationRationale = activity.shouldShowNotificationPermissionRationale()

        onAction(ActiveRunAction.SubmitLocationPermissionInfo(
            acceptedLocationPermission = context.hasLocationPermission(),
            shouldShowRationale = showLocationRationale
        ))

        onAction(ActiveRunAction.SubmitNotificationPermissionInfo(
            acceptedNotificationPermission = context.hasNotificationPermission(),
            shouldShowNotificationRationale = showNotificationRationale
        ))

        if(!showLocationRationale && !showNotificationRationale){
            permissionLauncher.requestCleanPermission(context)
        }
    }

    LaunchedEffect(state.isRunFinished) {
        if(state.isRunFinished){
            onServiceToggle(false)
        }
    }

    LaunchedEffect(key1 = state.shouldTrack) {
        if(context.hasLocationPermission() && state.shouldTrack && !ActiveRunService.isServiceActive){
            onServiceToggle(true)
        }
    }

    CleanScaffold(
        withGradientBackground = false,
        topAppBar = {
             CleanToolbar(
                showBackButton = true,
                title = stringResource(R.string.active_run),
                onBackClick = {
                    onAction(ActiveRunAction.OnBackClick)
                }
            )
        },
        floatingActionButton = {
            CleanFloatingAction(
                icon = if(state.shouldTrack){
                    StopIcon
                }else{
                    StartIcon
                },
                onClick = {
                    onAction(ActiveRunAction.OnToggleRunClick)
                },
                iconSize = 25.dp,
                contentDescription = if(state.shouldTrack){
                    stringResource(R.string.pause_run)
                }else{
                    stringResource(R.string.start_run)
                }
            )
        }
    ){ padding ->
        Box(modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)){
            TrackerMap(
                isRunFinished = state.isRunFinished,
                currentLocation = state.location,
                listLocation = state.runData.locations,
                onSnapshot = {},
                modifier = Modifier.fillMaxSize()
            )
            RunDataCard(
                runData = state.runData,
                elapsedTime = state.elapsedTime,
                modifier = Modifier.padding(16.dp)
                    .padding(padding)
                    .fillMaxWidth()
            )
        }
    }

    if(!state.shouldTrack && state.hasStartRunning){
        CleanDialog(
            title = stringResource(R.string.running_is_paused),
            onDismiss = {
                onAction(ActiveRunAction.OnResumeRunClick)
            },
            description = stringResource(R.string.resume_or_finish_run),
            primaryButton = {
                CleanActionButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.resume),
                    isLoadings = false ,
                ) {
                    onAction(ActiveRunAction.OnResumeRunClick)
                }
            },
            secondaryButton = {
                CleanOutlineActionButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.finish),
                    isLoadings = state.isSavingRun,
                ) {
                    onAction(ActiveRunAction.OnFinishRunClick)
                }
            }
        )
    }

    if(state.showLocationRationale || state.showNotificationRationale){
        CleanDialog(
            title = stringResource(R.string.permission_required ),
            onDismiss ={},
            description = when{
                state.showLocationRationale && state.showNotificationRationale -> {
                    stringResource(R.string.location_notification_rational)
                }
                state.showLocationRationale ->{
                    stringResource(R.string.location_rationale)
                }
                else ->{
                    stringResource(R.string.notification_rationale)
                }
            },
            primaryButton = {
                CleanOutlineActionButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.okay),
                    isLoadings = false ,
                    enabled = true
                ) {
                    onAction(ActiveRunAction.DismissRationaleDialog)
                    permissionLauncher.requestCleanPermission(context)
                }
            }
        ) { }
    }

}

private fun ActivityResultLauncher<Array<String>>.requestCleanPermission(
    context: Context
){
    val hasLocationPermission = context.hasLocationPermission()
    val hasNotificationPermission = context.hasNotificationPermission()
    val locationPermission = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    val notificationPermission = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
        arrayOf(
            Manifest.permission.POST_NOTIFICATIONS
        )
    }else{
        arrayOf()
    }

    when{
        !hasLocationPermission && !hasNotificationPermission -> {
            launch(locationPermission + notificationPermission)
        }
        !hasLocationPermission -> {
            launch(locationPermission)
        }
        !hasNotificationPermission -> {
            launch(notificationPermission)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.CUPCAKE)
@Preview

@Composable

private fun ActiveRunScreenPreview() {
    CleanTheme{
       ActiveRunScreen(
           state = ActiveRunState(),
           onAction = {}
       )

   }

}