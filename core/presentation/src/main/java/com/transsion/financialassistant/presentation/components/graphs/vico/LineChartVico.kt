package com.transsion.financialassistant.presentation.components.graphs.vico

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.transsion.financialassistant.presentation.components.graphs.model.DataPoint
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme

val sampleData = listOf(
    DataPoint("Sun", 120f),
    DataPoint("Mon", 300f),
    DataPoint("Tue", 180f),
    DataPoint("Wed", 400f),
    DataPoint("Thu", 250f),
    DataPoint("Fri", 310f),
    DataPoint("Sat", 200f),
)

@Composable
fun LineChartVico() {
}


@Preview(showBackground = true)
@Composable
fun PrevVicoLineChart() {
    FinancialAssistantTheme {
        LineChartVico()
    }
}
