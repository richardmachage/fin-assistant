package com.transsion.financialassistant.insights.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.sp
import com.transsion.financialassistant.presentation.components.graphs.custom.sampleData
import com.transsion.financialassistant.presentation.components.graphs.model.DataPoint
import com.transsion.financialassistant.presentation.components.graphs.vico.LineChartVico
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import com.transsion.financialassistant.presentation.components.texts.FaintText
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingLarge

@Composable
fun Graph(
    modifier: Modifier,
    title: String,
    subtitle: String,
    dataPoints: List<DataPoint> = sampleData,
    lineColor: Color = FAColors.green,
    bottomValueFormatter: (value: String) -> String = { value -> value }

) {
    if (dataPoints.isNotEmpty()) {
        ElevatedCard(
            modifier = modifier,
            shape = RoundedCornerShape(10)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingLarge)
            ) {
                //Tittle
                BigTittleText(text = title, textColor = lineColor)
                VerticalSpacer(5)
                //subtitle, date descriptions
                FaintText(
                    text = subtitle,
                    fontSize = 13.sp
                )
                VerticalSpacer(5)

                HorizontalDivider()

                LineChartVico(
                    dataPoints = dataPoints,
                    lineColor = lineColor,
                    bottomValueFormatter = bottomValueFormatter
                )
            }
        }
    }
}


@PreviewLightDark
@Preview()
@Composable
fun GraphPrev() {
    FinancialAssistantTheme {
        Scaffold {
            Graph(
                title = stringResource(com.transsion.financialassistant.presentation.R.string.money_in),
                subtitle = "From 27 Mar - 2 Apr, 2025, 9:50AM",
                modifier = Modifier.padding(it)
            )
        }
    }
}