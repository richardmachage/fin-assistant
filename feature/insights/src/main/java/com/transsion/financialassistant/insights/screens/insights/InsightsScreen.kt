package com.transsion.financialassistant.insights.screens.insights

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.transsion.financialassistant.data.models.InsightCategory
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.utils.toMonthDayDate
import com.transsion.financialassistant.insights.R
import com.transsion.financialassistant.insights.model.InsightCategoryCardItem
import com.transsion.financialassistant.insights.model.InsightTimeline
import com.transsion.financialassistant.insights.navigation.InsightsRoutes
import com.transsion.financialassistant.insights.screens.components.Graph
import com.transsion.financialassistant.insights.screens.components.InOutCard
import com.transsion.financialassistant.insights.screens.components.InsightCategoryCard
import com.transsion.financialassistant.insights.screens.components.MoneyToggle
import com.transsion.financialassistant.presentation.components.buttons.IconButtonFa
import com.transsion.financialassistant.presentation.components.graphs.custom.StackedBarChart
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme
import com.transsion.financialassistant.presentation.utils.HorizontalSpacer
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingMedium
import com.transsion.financialassistant.presentation.utils.paddingSmall

@Composable
fun InsightsScreen(
    navController: NavController,
    viewModel: InsightsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val graphData by viewModel.graphDataFlow.collectAsStateWithLifecycle()
    val categoryDistribution by viewModel.categoryDistributionFlow.collectAsStateWithLifecycle()
    val screeHeight = LocalConfiguration.current.screenHeightDp


    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                // .height(screeHeight.dp)
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding(),
                    start = paddingMedium,
                    end = paddingMedium
                )
                .verticalScroll(rememberScrollState())
        ) {
            //tittle
            BigTittleText(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(paddingSmall),
                text = stringResource(R.string.insights)
            )

            //personal finances and timeline selection
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingSmall),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                var showMenu by remember { mutableStateOf(false) }
                //personal finances/business switch
                Row(verticalAlignment = Alignment.CenterVertically) {

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        InsightCategory.entries.forEach {
                            DropdownMenuItem(
                                text = { Text(text = stringResource(it.description)) },
                                onClick = {
                                    if (state.insightCategory != it) {
                                        viewModel.switchInsightCategory(it)
                                    }
                                    showMenu = false
                                }
                            )
                        }
                    }

                    NormalText(text = stringResource(state.insightCategory.description))
                    HorizontalSpacer(5)
                    IconButtonFa(
                        onClick = {
                            showMenu = !showMenu
                        },
                        icon = Icons.Default.KeyboardArrowDown,
                        colors = IconButtonDefaults.iconButtonColors()
                            .copy(containerColor = Color.Transparent)
                    )

                }

                //Timeline switch
                var showTimelineMenu by remember { mutableStateOf(false) }
                OutlinedButton(
                    modifier = Modifier
                        .height(35.dp)
                        .padding(),
                    onClick = {
                        showTimelineMenu = !showTimelineMenu
                    }
                ) {

                    //val timelineOptions = listOf("Today", "This week", "This month")
                    DropdownMenu(
                        expanded = showTimelineMenu,
                        onDismissRequest = { showTimelineMenu = false }
                    ) {
                        InsightTimeline.entries.forEach {
                            DropdownMenuItem(
                                text = { Text(text = stringResource(it.description)) },
                                onClick = {
                                    if (state.insightTimeline != it) {
                                        viewModel.switchInsightTimeline(it)
                                    }
                                    showTimelineMenu = false
                                    Log.d(
                                        "TAG",
                                        "date in VM start : ${state.insightTimeline.getTimeline().startDate} end: ${state.insightTimeline.getTimeline().endDate}"
                                    )

                                }
                            )
                        }
                    }
                    Icon(
                        modifier = Modifier.size(18.dp),
                        painter = painterResource(com.transsion.financialassistant.presentation.R.drawable.calendar),
                        contentDescription = "calender"
                    )
                    HorizontalSpacer(5)
                    NormalText(text = stringResource(state.insightTimeline.description))
                }
            }

            VerticalSpacer(5)
           /* when personal finance collapse money in and out cards and the toggle buttons*/
            when(state.insightCategory){
                InsightCategory.PERSONAL -> {
                    //Money in and Out summary Card
                    InOutCard(
                        modifier = Modifier.padding(paddingSmall),
                        moneyIn = state.moneyIn ?: "0.0",
                        moneyOut = state.moneyOut ?: "0.0",
                        transactionsIn = state.transactionsIn ?: "0",
                        transactionsOut = state.transactionsOut ?: "0"
                    )
                    VerticalSpacer(5)

                    //Money In/Out toggle buttons
                    MoneyToggle(
                        selectedOption = state.transactionCategory,
                        onOptionSelected = {
                            viewModel.switchTransactionCategory(it)
                        }
                    )
                }
                InsightCategory.BUSINESS -> {
                    //Money in and Out summary Card
                }
            }


            //Graph
            VerticalSpacer(5)
            Graph(
                title = state.transactionCategory.description,
                subtitle = state.insightTimeline.getTimeline().displayInfo,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingMedium)
                    .align(Alignment.CenterHorizontally),
                lineColor = when (state.transactionCategory) {
                    TransactionCategory.IN -> FAColors.green
                    TransactionCategory.OUT -> Color.Red
                },
                dataPoints = graphData,
                bottomValueFormatter = { value ->
                    value.toMonthDayDate()
                }
            )

            //stackedBarchart
            VerticalSpacer(5)

            StackedBarChart(
                categories = categoryDistribution
            )

            //categories
            LazyVerticalGrid(
                columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(2),
                modifier = Modifier
                    .height((screeHeight / 2).dp)
                    .padding(paddingMedium),
                horizontalArrangement = Arrangement.SpaceBetween.apply {
                    spacedBy(6.dp)
                }
            ) {

                items(categoryDistribution) { item ->
                    InsightCategoryCard(
                        modifier = Modifier.padding(paddingMedium),
                        item = InsightCategoryCardItem(
                            tittle = item.name,
                            amount = item.amount.toString(),
                            categoryIcon = item.icon
                                ?: com.transsion.financialassistant.presentation.R.drawable.weui_arrow_outlined
                        ),
                        onClick = {
                            navController.navigate(
                                InsightsRoutes.CategoryInsights(
                                    category = item.name,
                                    startDate = state.insightTimeline.getTimeline().startDate,
                                    endDate = state.insightTimeline.getTimeline().endDate,
                                    timeLine = state.insightTimeline.getTimeline().displayInfo
                                )
                            )
                        }
                    )
                }

                if (state.transactionCategory == TransactionCategory.OUT) {
                    items(1) {
                        InsightCategoryCard(
                            modifier = Modifier.padding(paddingMedium),
                            item = InsightCategoryCardItem(
                                tittle = stringResource(R.string.transaction_costs),
                                amount = state.totalTransactionCost ?: "0.0",
                                categoryIcon = com.transsion.financialassistant.presentation.R.drawable.weui_arrow_outlined
                            ),
                            onClick = {
                                /*navController.navigate(
                                InsightsRoutes.CategoryInsights(
                                    category = "transaction_costs",
                                    startDate = state.insightTimeline.getTimeline().startDate,
                                    endDate = state.insightTimeline.getTimeline().endDate,
                                    timeLine = state.insightTimeline.getTimeline().displayInfo
                                )
                            )*/
                            }
                        )

                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun InsightsPrev() {
    FinancialAssistantTheme {
        //InsightsScreen()
    }
}