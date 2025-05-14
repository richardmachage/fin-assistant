package com.transsion.financialassistant.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)


object FAColors {
    val green = Color(0xFF01802D)
    val lightGreen = Color(0xFFC6DCD0)
    val black = Color(0xFF000000)
    val GrayBackground = Color(0xFFECEEED)
    val greenOverlay = Color(0xFFE4ECE6)//green.copy(alpha = 0.3f)
    val faintText = Color(0xFF757575)
    val cardBackgroundDark = Color(0xFF1E1E1E)
    val cardBackgroundLight = Color(0xFFECEEED)
    val splashScreenBackground = Color(0xFF413503)
}

enum class ListColors(val color: Color) {
    LAVENDER(Color(0xFF790F7C)),
    TEAL(Color(0xFF068196)),
    ORANGE(Color(0XFFDF8E02)),
    PURPLE(Color(0xFF8B2CF8)),
    BLUE(Color(0xFF1B0BFF)),
    PEACH(Color(0xFFFD675B)),
    JUNGLE_GREEN(Color(0xFF767600))
}


@Composable
fun ThemeColors(){
    val cardBackground =
        if (isSystemInDarkTheme()) FAColors.cardBackgroundDark else FAColors.cardBackgroundLight
}

