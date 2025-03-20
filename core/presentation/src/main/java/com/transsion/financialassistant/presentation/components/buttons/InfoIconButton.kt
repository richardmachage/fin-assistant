package com.transsion.financialassistant.presentation.components.buttons

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.transsion.financialassistant.presentation.R

@Composable
fun InfoIconButton(
    onClick: () -> Unit = {}
){
    IconButton(
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors().copy(
            contentColor = MaterialTheme.colorScheme.onBackground,
            containerColor = Color(0xE5E2E2A6)
        )
    ) {
        Icon(
            painter = painterResource(R.drawable.stash_question),
            contentDescription = "Info",
        )
    }
}