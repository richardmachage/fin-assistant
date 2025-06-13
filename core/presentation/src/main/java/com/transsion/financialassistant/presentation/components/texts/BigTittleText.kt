package com.transsion.financialassistant.presentation.components.texts

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun BigTittleText(
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
    text: String = "Begin your smartmoney journey",
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    fontSize: TextUnit = 30.sp,
    fontWeight: FontWeight = FontWeight(600)
) {
    Text(
        text = text,
        fontWeight = fontWeight,
        fontSize = fontSize,
        lineHeight = 31.sp,
        textAlign = textAlign,
        color = textColor
    )
}