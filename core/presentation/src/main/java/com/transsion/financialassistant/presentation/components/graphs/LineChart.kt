package com.transsion.financialassistant.presentation.components.graphs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme

@Composable
fun LineChart(
    modifier: Modifier = Modifier,
    dataPoints: List<DataPoint>,
) {

    val selectedPoint = remember { mutableStateOf<DataPoint?>(null) }
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val spacingX = screenWidth / 3
    val scrollState = rememberScrollState()


    BoxWithConstraints(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {


        val spacingPerPoint = 16.dp
        val chartWidth = (dataPoints.size * spacingPerPoint.value).coerceAtLeast(800F).dp

        val canvasHeight = constraints.maxHeight.toFloat()

        val size = Size(chartWidth.value, canvasHeight)

        val offsets = remember(dataPoints, size) { mapToOffsets(dataPoints, size, spacingX.value) }

        val labelSpace = 24f
        Row(
            modifier = Modifier
                .height(canvasHeight.dp)
                .horizontalScroll(scrollState)
        ) {
            Canvas(
                modifier = Modifier
                    //.fillMaxSize()
                    .width(chartWidth)
                    .height(canvasHeight.dp + labelSpace.dp)
                    .pointerInput(dataPoints) {
                        /**This lets allows for: Detecting a tap, Find which Offset is closest in x,Store the associated DataPoint*/
                        detectTapGestures { tapOffset ->
                            // Find closest data point to the tapped X
                            val closest = offsets.mapIndexed { index, offset ->
                                index to kotlin.math.abs(tapOffset.x - offset.x)
                            }
                                .minByOrNull { it.second }?.first
                            selectedPoint.value = closest?.let { dataPoints[it] }
                        }
                    }
            )
            {

                //.dp.toPx()


                val path = Path()
                if (offsets.size >= 2) {
                    path.moveTo(offsets[0].x, offsets[0].y)
                    val smoothing = 0.15f

                    for (i in 1 until offsets.lastIndex) {
                        val p0 = offsets[i - 1]
                        val p1 = offsets[i]
                        val p2 = offsets[i + 1]

                        val control1 = Offset(
                            p0.x + (p1.x - p0.x) * smoothing,
                            p0.y
                        )
                        val control2 = Offset(
                            p1.x - (p2.x - p0.x) * smoothing,
                            p1.y
                        )

                        path.cubicTo(
                            control1.x, control1.y,
                            control2.x, control2.y,
                            p1.x, p1.y
                        )
                    }

                    // Connecting last point directly to ensure tail is smooth
                    path.lineTo(offsets.last().x, offsets.last().y)

                    drawPath(
                        path = path,
                        color = Color(0xFFFF9800),
                        style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
                    )

                    val gridLineTop = 0f
                    val gridLineBottom = size.height + labelSpace - (labelSpace / 4)
                    val dashedPaint = Paint().asFrameworkPaint().apply {
                        color = Color.Gray.copy(alpha = 0.3f).toArgb()
                        strokeWidth = 2.dp.toPx()
                        isAntiAlias = true
                        pathEffect = android.graphics.DashPathEffect(floatArrayOf(25f, 20f), 0f)
                    }

                    //vertical grid lines
                    offsets.forEach { point ->
                        drawContext.canvas.nativeCanvas.drawLine(
                            point.x,
                            gridLineTop,
                            point.x,
                            gridLineBottom,
                            dashedPaint
                        )
                    }

                    //horizontal divider line
                    drawLine(
                        color = Color.Gray.copy(alpha = 0.3f),
                        start = Offset(offsets.first().x, offsets.first().y + labelSpace),
                        end = Offset(
                            offsets.last().x,
                            offsets.first().y + labelSpace/*size.height + labelSpace*/
                        ),
                        strokeWidth = 2.dp.toPx()
                    )

                    //draw the x-axis labels
                    val textPaint = Paint().asFrameworkPaint().apply {
                        isAntiAlias = true
                        textSize = 16.sp.toPx()
                        color = android.graphics.Color.GRAY
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                    dataPoints.forEachIndexed { index, point ->
                        val offset = offsets.getOrNull(index) ?: return@forEachIndexed

                        drawContext.canvas.nativeCanvas.drawText(
                            point.x, // e.g., "Sun"
                            offset.x,
                            size.height + labelSpace + (labelSpace * 2), // space below canvas for the label
                            textPaint
                        )
                    }

                    //marker
                    selectedPoint.value?.let { point ->
                        val index = dataPoints.indexOf(point)
                        val offset = offsets.getOrNull(index) ?: return@let

                        // Marker dot
                        drawCircle(
                            color = Color(0xFF4CAF50),
                            radius = 6.dp.toPx(),
                            center = offset
                        )

                        // Tooltip background
                        drawRoundRect(
                            color = Color.White,
                            topLeft = Offset(offset.x + 8.dp.toPx(), offset.y - 40.dp.toPx()),
                            size = Size(120.dp.toPx(), 40.dp.toPx()),
                            cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx()),
                            style = Fill,
                            alpha = 0.95f
                        )
                        // Tooltip text
                        val tooltipPaint = Paint().asFrameworkPaint().apply {
                            isAntiAlias = true
                            textSize = 14.sp.toPx()
                            color = android.graphics.Color.BLACK
                        }
                        drawContext.canvas.nativeCanvas.drawText(
                            "${point.x}, ${point.y.toInt()}",
                            offset.x + 14.dp.toPx(),
                            offset.y - 14.dp.toPx(),
                            tooltipPaint
                        )
                    }
                }
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
    DataPoint("Sat", 200f),
)

@Preview(showBackground = true, showSystemUi = true)
@PreviewLightDark
@Composable
fun LineChartPrev(showBackground: Boolean = true) {
    FinancialAssistantTheme {
        Scaffold {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    //.fillMaxHeight(0.5f)
                    .padding(it),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    LineChart(
                        dataPoints = sampleData,
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(200.dp)
                    )
                }
            }

        }
    }

}