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
fun TitleText(
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
    text: String,
    fontSize: TextUnit = 16.sp,
    textColor: Color = MaterialTheme.colorScheme.onBackground
) {
    Text(
        modifier = modifier,
        text = text,
        fontWeight = FontWeight(600),
        fontSize = fontSize,
        lineHeight = 22.sp,
        textAlign = textAlign,
        color = textColor
    )
}