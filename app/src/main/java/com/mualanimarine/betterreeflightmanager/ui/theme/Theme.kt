package com.mualanimarine.betterreeflightmanager.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = Lagoon,
    secondary = Amber,
    tertiary = Slate,
    background = Mist,
    surface = White,
    surfaceVariant = Foam,
    primaryContainer = ReefSand,
    secondaryContainer = Foam
)

private val DarkColors = darkColorScheme(
    primary = Amber,
    secondary = Lagoon,
    tertiary = White,
    background = DeepOcean,
    surface = Slate
)

@Composable
fun BetterReefLightManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = AppTypography,
        content = content
    )
}

