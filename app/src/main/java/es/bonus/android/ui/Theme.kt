package es.bonus.android.ui

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.drawBackground
import androidx.ui.foundation.isSystemInDarkTheme
import androidx.ui.layout.fillMaxSize
import androidx.ui.material.MaterialTheme
import androidx.ui.material.darkColorPalette
import androidx.ui.material.lightColorPalette

private val DarkColorPalette = darkColorPalette(
    background = Colors.darkBackground,
    primary = Colors.white1,
    primaryVariant = Colors.gray1,
    secondary = Colors.accent
)

@Composable
fun BonusTheme(content: @Composable() () -> Unit) {
    val colors = DarkColorPalette

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}