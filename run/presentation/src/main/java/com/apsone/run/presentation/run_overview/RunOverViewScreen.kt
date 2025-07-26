@file:OptIn(ExperimentalMaterial3Api::class)

package com.apsone.run.presentation.run_overview

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apsone.core.presentation.designsystem.AnalyticsIcon
import com.apsone.core.presentation.designsystem.CleanTheme
import com.apsone.core.presentation.designsystem.LogoIcon
import com.apsone.core.presentation.designsystem.LogoutIcon
import com.apsone.core.presentation.designsystem.R
import com.apsone.core.presentation.designsystem.RunIcon
import com.apsone.core.presentation.designsystem.components.CleanFloatingAction
import com.apsone.core.presentation.designsystem.components.CleanScaffold
import com.apsone.core.presentation.designsystem.components.CleanToolbar
import com.apsone.core.presentation.designsystem.components.DropDownItem
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.HONEYCOMB_MR2)
@Composable
fun RunOverViewScreenRot(
    onStartRunClick:() -> Unit = {},
    viewModel: RunOverviewViewModel = koinViewModel()
) {
    RunOverViewScreen(onAction = {
        when(it){
            is RunOverviewAction.OnStartClick -> onStartRunClick()
            else -> {}
        }
        viewModel.onAction(it)
    })

}

@RequiresApi(Build.VERSION_CODES.HONEYCOMB_MR2)
@Composable
private fun RunOverViewScreen(
    onAction: (RunOverviewAction) -> Unit
) {

    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
    CleanScaffold(
        topAppBar = {
            CleanToolbar(
                showBackButton = false,
                title = stringResource(R.string.runique),
                scrollBehavior = scrollBehavior,
                menusItem = listOf(
                    DropDownItem(
                        icon = AnalyticsIcon,
                        title = stringResource(R.string.analytic)
                    ),
                    DropDownItem(
                        icon = LogoutIcon,
                        title = stringResource(R.string.logout)
                    )
                ),
                onMenuItemClick = { index ->
                    when(index){
                        0 -> onAction(RunOverviewAction.OnAnalyticsClick)
                        1 -> onAction(RunOverviewAction.OnLogoutClick)
                    }
                },
                startContent = {
                    Icon(
                        imageVector = LogoIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(30.dp)
                    )

                })
        },
        floatingActionButton = {
            CleanFloatingAction(
                icon = RunIcon,
                onClick = { onAction(RunOverviewAction.OnStartClick) }
            )
        }
    ) { padding ->

    }

}

@RequiresApi(Build.VERSION_CODES.CUPCAKE)
@Preview
@Composable
private fun RunOverViewScreenPreview() {
    CleanTheme{
       RunOverViewScreen(
           onAction = {}
       )

   }

}