package com.transsion.financialassistant.presentation.components.texts

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun NormalText(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    textAlign: TextAlign = TextAlign.Center,
) {
    Text(
        modifier = modifier,
        textAlign = textAlign,
        text = text,
        fontWeight = FontWeight(400),
        fontSize = 14.sp,
        lineHeight = 17.sp,
        color = textColor
    )
}