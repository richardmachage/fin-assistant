package com.transsion.financialassistant.presentation.components.graphs.vico

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.transsion.financialassistant.presentation.components.graphs.custom.sampleData
import com.transsion.financialassistant.presentation.components.graphs.model.DataPoint
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme
import kotlinx.coroutines.runBlocking


@Composable
fun LineChartVico(
    modifier: Modifier = Modifier,
    dataPoints: List<DataPoint>,
    lineColor: Color = Color(0xFFFCA251),//FAColors.green,
    bottomValueFormatter: (value: String) -> String = { value -> value }
) {
    val marker = rememberMarker(
        valueFormatter = getValueFormatter(
            dataPoints = dataPoints,
            xFormater = bottomValueFormatter
        )
    )

    val modelProducer = remember { CartesianChartModelProducer() }

    LaunchedEffect(dataPoints) {
        modelProducer.runTransaction {
            lineSeries {
                series(dataPoints.map { it.y })
            }
        }
    }
    CartesianChartHost(
        chart = rememberCartesianChart(
            rememberLineCartesianLayer(
                lineProvider = LineCartesianLayer.LineProvider.series(
                    LineCartesianLayer.rememberLine(
                        fill = LineCartesianLayer.LineFill.single(fill(lineColor)),
                        pointConnector = LineCartesianLayer.PointConnector.cubic(),
                        areaFill = LineCartesianLayer.AreaFill.single((fill(lineColor.copy(alpha = 0.2f))))
                    ),
                ),
            ),
            startAxis = VerticalAxis.rememberStart(
                line = null,
                guideline = null,
                tick = null,
            ),
            bottomAxis = HorizontalAxis.rememberBottom(
                tick = null,
                valueFormatter = { context, value, verticalAxisPosition ->
                    try {
                        bottomValueFormatter(dataPoints[value.toInt()].x)
                    } catch (e: Exception) {
                        "j"
                    }
                }
            ),
            marker = marker,
        ),
        modelProducer = modelProducer,
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun PrevVicoLineChart() {

    FinancialAssistantTheme {

        val modelProducer = remember { CartesianChartModelProducer() }
        runBlocking {
            modelProducer.runTransaction {
                lineSeries {
                    series(sampleData.map { it.y })
                }
            }
        }
        LineChartVico(dataPoints = sampleData)
    }
}

