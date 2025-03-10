package com.transsion.financialassistant.presentation.components.texts

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.transsion.financialassistant.presentation.theme.FAColors

@Preview(showSystemUi = true)
@Composable
fun FaintText(
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
    text: String = "Financial Assistant has detected your primary M-PESA phone number as",
    textColor: Color = FAColors.faintText
) {
    Text(
        text = text,
        fontWeight = FontWeight(400),
        fontSize = 14.sp,
        lineHeight = 17.sp,
        textAlign = textAlign,
        color = textColor,
        letterSpacing = 1.sp
    )
}