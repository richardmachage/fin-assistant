package com.transsion.financialassistant.presentation.components.graphs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme

@Composable
fun LineChart(
    modifier: Modifier = Modifier,
    dataPoints: List<DataPoint>,
) {
    BoxWithConstraints(modifier = modifier) {
        val canvasWidth = constraints.maxWidth.toFloat()
        val canvasHeight = constraints.maxHeight.toFloat()

        val size = Size(canvasWidth, canvasHeight)

        val offsets = remember(dataPoints) { mapToOffsets(dataPoints, size) }

        Canvas(modifier = Modifier.fillMaxSize()) {
            for (i in 0 until offsets.size - 1) {
                drawLine(
                    color = Color(0xFFFF9800),
                    start = offsets[i],
                    end = offsets[i + 1],
                    strokeWidth = 4f,
                    cap = StrokeCap.Round
                )
            }
        }

    }
}

val sampleData = listOf(
    DataPoint("Sun", 120f),
    DataPoint("Mon", 300f),
    DataPoint("Tue", 180f),
    DataPoint("Wed", 400f),
    DataPoint("Thu", 250f),
    DataPoint("Fri", 310f),
    DataPoint("Sat", 200f)
)

@Preview
@Composable
fun LineChartPrev(showBackground: Boolean = true) {
    FinancialAssistantTheme {
        Scaffold {
            LineChart(
                dataPoints = sampleData,
                modifier = Modifier
                    .padding(it)
                    .fillMaxWidth()
                    .height(300.dp)
            )
        }
    }

}