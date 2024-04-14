package com.plcoding.daggerhiltcourse.ui.theme

import androidx.compose.ui.graphics.Color

sealed class ThemeColors(
    val background: Color,
    val surface: Color,
    val primary: Color,
    val text: Color,
) {
    object Night: ThemeColors(
        background = Color(0xFF333333),
        surface = Color(0xFF444444),
        primary = Color(0xFFFFFFFF),
        text = Color(0xFF000000),
    )
    object Day: ThemeColors(
        background = Color(0xFFFFFFFF),
        surface = Color(0xFFFFFFFF),
        primary = Color(0xFF000000),
        text = Color(0xFFFFFFFF),
    )
}