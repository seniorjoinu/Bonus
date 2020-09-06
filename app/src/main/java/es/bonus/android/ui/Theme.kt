package es.bonus.android.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable


private val DarkColorPalette = darkColors(
    background = Colors.darkBackground,
    primary = Colors.white1,
    primaryVariant = Colors.gray1,
    secondary = Colors.accent
)

@Composable
fun BonusTheme(content: @Composable () -> Unit) {
    val colors = DarkColorPalette

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}