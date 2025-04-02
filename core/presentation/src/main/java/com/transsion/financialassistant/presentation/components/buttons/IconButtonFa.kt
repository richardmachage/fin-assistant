package com.transsion.financialassistant.presentation.components.buttons

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import com.transsion.financialassistant.presentation.theme.FAColors

@Composable
fun IconButtonFa(
    modifier: Modifier = Modifier,
    icon: Painter,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors().copy(
        containerColor = FAColors.faintText.copy(0.3F)
    )
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        colors = colors,
        enabled = enabled
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
        )
    }
}


@Composable
fun IconButtonFa(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors().copy(
        containerColor = FAColors.faintText.copy(0.3F)
    )
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        colors = colors,
        enabled = enabled
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
        )
    }

}