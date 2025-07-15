package com.nezuko.ui.theme

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun TestTaskTheme(
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            dynamicLightColorScheme(context)
        }

        else -> LightColorScheme
    }.copy(
        background = Color.White,
        surface = Color.White,
        surfaceVariant = Color.White,
        onSurface = Color.Black,
        onSurfaceVariant = Color.Gray,

        primary = Color.Cyan,
        onPrimary = Color.White,
        primaryContainer = Color.White,
        onPrimaryContainer = Color.Black,

        secondary = Color(0xFF03DAC6),
        onSecondary = Color.Black,
        secondaryContainer = Color.White,
        onSecondaryContainer = Color.Black,

        tertiary = Color(0xFF03DAC6),
        onTertiary = Color.White,
        tertiaryContainer = Color.White,
        onTertiaryContainer =Color.Black,
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

fun ComponentActivity.setStatusBarColor(color: Color = Color.White, useDarkIcons: Boolean = true) {
    WindowCompat.setDecorFitsSystemWindows(window, false) // Для edge-to-edge

    window.statusBarColor = color.toArgb()

    WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = useDarkIcons
}