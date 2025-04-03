package com.transsion.financialassistant.insights.screens.insights

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
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.insights.R
import com.transsion.financialassistant.insights.model.InsightCategory
import com.transsion.financialassistant.insights.model.InsightTimeline
import com.transsion.financialassistant.insights.screens.components.Graph
import com.transsion.financialassistant.insights.screens.components.InOutCard
import com.transsion.financialassistant.insights.screens.components.InsightCategoryCard
import com.transsion.financialassistant.insights.screens.components.MoneyToggle
import com.transsion.financialassistant.presentation.components.buttons.IconButtonFa
import com.transsion.financialassistant.presentation.components.graphs.custom.StackedBarChart
import com.transsion.financialassistant.presentation.components.graphs.custom.sampleCategories
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
    viewModel: InsightsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
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
                //var currentTimeline by remember { mutableStateOf("Today") }
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
            //Money in and Out summary Card
            InOutCard(
                modifier = Modifier.padding(paddingSmall),
                moneyIn = state.moneyIn ?: "0.0",
                moneyOut = "177,500.90",
                transactionsIn = state.transactionsIn ?: "0",
                transactionsOut = "256"
            )
            VerticalSpacer(5)

            //Money In/Out toggle buttons
            MoneyToggle(
                selectedOption = state.transactionCategory,
                onOptionSelected = {
                    viewModel.switchTransactionCategory(it)
                }
            )

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
                }
            )

            //stackedBarchart
            VerticalSpacer(5)

            StackedBarChart(
                categories = sampleCategories
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

                items(viewModel.dummyInsightCategories) { item ->
                    InsightCategoryCard(
                        modifier = Modifier.padding(paddingMedium),
                        item = item,
                        onClick = {
                            //TODO: navigate to  specific category screen
                        }
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun InsightsPrev() {
    FinancialAssistantTheme {
        InsightsScreen()
    }
}