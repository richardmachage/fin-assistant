package com.transsion.financialassistant.presentation.components.graphs

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

fun mapToOffsets(dataPoints: List<DataPoint>, size: Size, spacingX: Float): List<Offset> {
    val maxY = dataPoints.maxOf { it.y }
    val minY =
        dataPoints.minOf { it.y } // if we will ever want zero-based scaling, we just set this to 0f
    val verticalRange = maxY - minY


    // val spacingX = 16.dp.value//size.width / (dataPoints.size - 1)

    return dataPoints.mapIndexed { index, point ->
        val x = index * spacingX
        val normalizedY = (point.y - minY) / verticalRange
        val y = size.height * (1f - normalizedY) //invert the Y axis
        Offset(x, y)
    }

}