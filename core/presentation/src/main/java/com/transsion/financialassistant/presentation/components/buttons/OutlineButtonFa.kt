package com.transsion.financialassistant.presentation.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.transsion.financialassistant.presentation.theme.FAColors

@Preview(showSystemUi = true)
@Composable
fun OutlineButtonFa(
    text: String = "Save",
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors().copy(
            contentColor = FAColors.green
        ),
        enabled = enabled,
        border = BorderStroke(1.dp, FAColors.green)
    ) {
        Text(text)
    }

}