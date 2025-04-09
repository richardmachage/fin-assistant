package com.transsion.financialassistant.presentation.components.buttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme


@Composable
fun CancelButton(
    onClick: () -> Unit = {}
){
    IconButton(
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors().copy(
            contentColor = MaterialTheme.colorScheme.onBackground, // FAColors.GrayBackground,
            containerColor = Color(0xE5E2E2A6)// MaterialTheme.colorScheme.background
        )
    ) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "Cancel",
           // tint = MaterialTheme.colorScheme.onBackground
        )
    }
}

@PreviewLightDark
@Composable
fun CancelButtonPreview(){
    FinancialAssistantTheme {
        CancelButton()
    }
}

