package com.apsone.core.presentation.designsystem

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat

val DarkColorScheme = darkColorScheme(
    primary = CleanGreen,
    background = CleanBlack,
    surface = CleanDarkGray,
    secondary = CleanWhite,
    tertiary = CleanWhite,
    primaryContainer = CleanGreen30,
    onPrimary = CleanBlack,
    onBackground = CleanWhite,
    onSurface = CleanWhite,
    onSurfaceVariant = CleanGray,
    error = CleanDarkRed
)

@RequiresApi(Build.VERSION_CODES.CUPCAKE)
@Composable
fun CleanTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode){
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window,view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
