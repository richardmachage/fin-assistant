package com.transsion.financialassistant.presentation.components.graphs.custom

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.transsion.financialassistant.presentation.components.graphs.model.CategoryDistribution
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme
import com.transsion.financialassistant.presentation.utils.HorizontalSpacer
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingMedium

@Composable
fun StackedBarChart(
    categories: List<CategoryDistribution>,
    modifier: Modifier = Modifier,
    barHeight: Int = 34,
    legendHeight: Int = 80
) {
    var showLegend by remember { mutableStateOf(true) }
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(10),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(if (showLegend) paddingMedium else 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //chart
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(barHeight.dp)
                    .clip(RoundedCornerShape(20))
                    .clickable {
                        showLegend = !showLegend
                    }
            ) {
                categories.forEach { category ->
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(category.percentage)
                            .background(category.color)
                    )
                }
            }
            VerticalSpacer(5)

            //Legend
            AnimatedVisibility(showLegend) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .height(legendHeight.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    categories.forEach {
                        LegendItem(it)
                    }
                }
            }


        }
    }

}

@Composable
fun LegendItem(category: CategoryDistribution) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(category.color, shape = RoundedCornerShape(4.dp))
        )
        HorizontalSpacer(10)

        //label
        NormalText(text = category.name, modifier = Modifier.weight(1f), textAlign = TextAlign.Left)
        NormalText(text = "${category.percentage.toInt()}%")
    }
}

val sampleCategories = listOf(
    CategoryDistribution("General", 76f, Color(0xFF007F7F)),
    CategoryDistribution("Side-hustle", 13f, Color(0xFFFF4081)),
    CategoryDistribution("Gifts & Donations", 7f, Color(0xFFFFD700)),
    CategoryDistribution("Salary", 4f, Color(0xFF00CFFF))
)


@Preview(showSystemUi = true)
@Composable
fun StackedBarChartPreview() {
    FinancialAssistantTheme {
        Column(
            modifier = Modifier.padding(paddingMedium),

            ) {
            StackedBarChart(
                categories = sampleCategories,
            )
        }
    }
}