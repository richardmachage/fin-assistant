package com.transsion.financialassistant.presentation.components.buttons

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.transsion.financialassistant.presentation.theme.FAColors


@Preview(showSystemUi = true)
@Composable
fun FilledButtonFa(
    text: String = "Save",
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    modifier: Modifier = Modifier.fillMaxSize()
) {
    Button(
        modifier = Modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = FAColors.green,
            contentColor = Color.White
        ),
        enabled = enabled
    ) {
        Text(text)
    }
}

