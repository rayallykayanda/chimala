package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = SunsetOrange,
    onPrimary = MalachiteTeal,
    secondary = AmberGold,
    onSecondary = Color(0xFF1D192B),
    tertiary = TextTertiaryDark,
    onTertiary = Color(0xFF1D192B),
    background = DarkBg,
    onBackground = TextPrimaryDark,
    surface = CardBg,
    onSurface = TextPrimaryDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = TextSecondaryDark,
    outline = OutlineDark
)

private val LightColorScheme = lightColorScheme(
    primary = SunsetOrangeLight,
    onPrimary = Color.White,
    secondary = AmberGoldLight,
    onSecondary = Color.White,
    tertiary = MalachiteTealLight,
    onTertiary = Color.White,
    background = LightBg,
    onBackground = TextPrimaryLight,
    surface = LightCardBg,
    onSurface = TextPrimaryLight,
    surfaceVariant = Color(0xFFECECEF),
    onSurfaceVariant = TextSecondaryLight,
    outline = Color(0xFFA6A2B3)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // We default dynamicColor to false to showcase our custom authentic "Chimala Sunset Beats" theme
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
