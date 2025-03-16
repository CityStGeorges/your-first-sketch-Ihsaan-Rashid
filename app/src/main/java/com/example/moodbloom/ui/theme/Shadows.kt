package com.example.moodbloom.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Stable
data class Shadow(
    val elevation: Dp,
    val color: Color
)

@Immutable
data class Shadows(
    val light: Shadow,
    val medium: Shadow,
    val dark: Shadow,
    val none: Shadow
)

val LocalShadows = staticCompositionLocalOf {
    Shadows(
        light = Shadow(elevation = 1.dp, color = Color.Black),
        medium = Shadow(elevation = 2.dp, color = Color.Black),
        dark = Shadow(elevation = 4.dp, color = Color.Black),
        none = Shadow(elevation = 0.dp, color = Color.Transparent),
    )
}
