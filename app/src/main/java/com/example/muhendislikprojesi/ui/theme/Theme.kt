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

@Composable
fun getColor(id: Int): Color {
    return Color(ContextCompat.getColor(LocalContext.current, id))
}

@SuppressLint("ResourceAsColor")
private val DarkColors = darkColorScheme(
    primary = Color(R.color.KoyuMavi),
    onPrimary = Color(R.color.AcikMavi),
    primaryContainer = Color(R.color.AcikMavi),
    onPrimaryContainer = Color(R.color.Tenrengi),
    background = Color(R.color.Tenrengi),
    onBackground = Color(R.color.KoyuMavi),
    surface = Color(R.color.KoyuMavi),
    onSurface = Color(R.color.AcikMavi)
)

@SuppressLint("ResourceAsColor")
private val  LightColors = lightColorScheme(
    primary = Color(R.color.AcıkTenrengi),
    onPrimary = Color(R.color.DahaKoyuTenrengi),
    primaryContainer = Color(R.color.KoyuTenrengi),
    onPrimaryContainer = Color(R.color.AcıkTenrengi),
    background = Color(R.color.TuruncumsuTenrengi),
    onBackground = Color(R.color.KoyuTenrengi),
    surface = Color(R.color.KoyuTenrengi),
    onSurface = Color(R.color.DahaKoyuTenrengi)

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun MuhendislikProjesiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColors
        else -> LightColors
    }

    val view = LocalView.current
    val statusBarColor = colorResource(id = R.color.Gri).toArgb()
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