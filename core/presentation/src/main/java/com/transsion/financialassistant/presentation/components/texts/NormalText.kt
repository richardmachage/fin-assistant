package com.transsion.financialassistant.presentation.components.texts

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun NormalText(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = 14.sp,
    lineHeight: TextUnit = 17.sp,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    textAlign: TextAlign = TextAlign.Center,
    fontWeight: FontWeight = FontWeight(400)
) {
    Text(
        modifier = modifier,
        textAlign = textAlign,
        text = text,
        fontWeight = fontWeight,
        fontSize = fontSize,
        lineHeight = lineHeight,
        color = textColor
    )
}