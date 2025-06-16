package com.transsion.financialassistant.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.utils.paddingMedium

@Composable
fun LinearProgressBar(
    modifier: Modifier = Modifier,
    progress: Float
) {
    LinearProgressIndicator(
        modifier = modifier
            .padding(paddingMedium)
            .fillMaxWidth(),
        progress = { progress },
        color = FAColors.green,
        gapSize = (-10).dp,
        strokeCap = StrokeCap.Round,
        trackColor = FAColors.lightGreen,
        drawStopIndicator = {},
    )
}