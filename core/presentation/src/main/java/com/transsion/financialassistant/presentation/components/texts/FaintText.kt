package com.transsion.financialassistant.presentation.components.texts

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.transsion.financialassistant.presentation.theme.FAColors

@Composable
fun FaintText(
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
    text: String,
    textColor: Color = FAColors.faintText,
    fontSize: TextUnit = 14.sp,
    fontWeight: FontWeight = FontWeight(400)
) {
    Text(
        modifier = modifier,
        text = text,
        fontWeight = fontWeight,
        fontSize = fontSize,
        lineHeight = 17.sp,
        textAlign = textAlign,
        color = textColor,
        letterSpacing = 1.sp,
        style = TextStyle(fontWeight = fontWeight),
    )
}