package com.transsion.financialassistant.presentation.components.texts

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import com.transsion.financialassistant.presentation.theme.FAColors


@Composable
fun ClickableText(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    color: Color = FAColors.green,
    onClickEnabled: Boolean = true,
    fontSize: TextUnit = TextUnit.Unspecified,
    underline: Boolean = false
) {
    Text(
        modifier = modifier.clickable(
            enabled = onClickEnabled,
            onClick = onClick
        ),
        text = text,
        color = color,
        fontWeight = FontWeight.Bold,
        fontSize = fontSize,
        textDecoration = if (underline) TextDecoration.Underline else null
    )
}