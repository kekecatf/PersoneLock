package com.example.muhendislikprojesi.ui.theme

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.example.muhendislikprojesi.R
import androidx.compose.material3.*

@Composable
fun MuhendislikProjesiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val lightColors = lightColorScheme(
        primary = colorResource(id = R.color.light_primary),
        onPrimary = colorResource(id = R.color.light_onPrimary),
        primaryContainer = colorResource(id = R.color.light_primaryContainer),
        onPrimaryContainer = colorResource(id = R.color.light_onPrimaryContainer),
        background = colorResource(id = R.color.light_background),
        onBackground = colorResource(id = R.color.light_onBackground),
        surface = colorResource(id = R.color.light_surface),
        onSurface = colorResource(id = R.color.light_onSurface)
    )

    val darkColors = darkColorScheme(
        primary = colorResource(id = R.color.dark_primary),
        onPrimary = colorResource(id = R.color.dark_onPrimary),
        primaryContainer = colorResource(id = R.color.dark_primaryContainer),
        onPrimaryContainer = colorResource(id = R.color.dark_onPrimaryContainer),
        background = colorResource(id = R.color.dark_background),
        onBackground = colorResource(id = R.color.dark_onBackground),
        surface = colorResource(id = R.color.dark_surface),
        onSurface = colorResource(id = R.color.dark_onSurface)
    )

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> darkColors
        else -> lightColors
    }

    val view = LocalView.current
    val statusBarColor = colorScheme.primary.toArgb()
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = statusBarColor
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}


