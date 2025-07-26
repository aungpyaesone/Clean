package com.apsone.core.presentation.designsystem.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@RequiresApi(Build.VERSION_CODES.HONEYCOMB_MR2)
@Composable
fun CleanScaffold(
    modifier: Modifier = Modifier,
    withGradientBackground: Boolean = true,
    topAppBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = topAppBar,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = FabPosition.Center
    ){ padding ->
        if(withGradientBackground){
            GradientBackground{
                content(padding)
            }
        }else{
            content(padding)
        }
    }
}