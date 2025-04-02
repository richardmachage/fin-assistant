package com.transsion.financialassistant.insights.screens.insights

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.insights.R
import com.transsion.financialassistant.insights.screens.components.InOutCard
import com.transsion.financialassistant.insights.screens.components.MoneyToggle
import com.transsion.financialassistant.presentation.components.buttons.IconButtonFa
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme
import com.transsion.financialassistant.presentation.utils.HorizontalSpacer
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingSmall

@Composable
fun InsightsScreen() {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
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
                val menuOptions = listOf("Personal finances", "Business finances")
                var currentOption by remember { mutableStateOf(menuOptions[0]) }
                var showMenu by remember { mutableStateOf(false) }
                //personal finances/business switch
                Row(verticalAlignment = Alignment.CenterVertically) {

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        menuOptions.forEach {
                            DropdownMenuItem(
                                text = { Text(text = it) },
                                onClick = {
                                    currentOption = it
                                    showMenu = false
                                }
                            )
                        }

                    }

                    NormalText(text = currentOption)
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
                var currentTimeline by remember { mutableStateOf("Today") }
                var showTimelineMenu by remember { mutableStateOf(false) }
                OutlinedButton(
                    modifier = Modifier
                        .height(35.dp)
                        .padding(),
                    onClick = {
                        showTimelineMenu = !showTimelineMenu
                    }
                ) {

                    val timelineOptions = listOf("Today", "This week", "This month")
                    DropdownMenu(
                        expanded = showTimelineMenu,
                        onDismissRequest = { showTimelineMenu = false }
                    ) {
                        timelineOptions.forEach {
                            DropdownMenuItem(
                                text = { Text(text = it) },
                                onClick = {
                                    currentTimeline = it
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
                    NormalText(text = currentTimeline)
                }
            }

            VerticalSpacer(5)
            //Money in and Out summary Card
            InOutCard(
                modifier = Modifier.padding(paddingSmall),
                moneyIn = "236,900.90",
                moneyOut = "177,500.90",
                transactionsIn = "14",
                transactionsOut = "256"
            )
            VerticalSpacer(5)

            //Money In/Out toggle buttons
            var selectedMoneyToggleOption by remember { mutableStateOf(TransactionCategory.IN) }
            MoneyToggle(
                selectedOption = selectedMoneyToggleOption,
                onOptionSelected = { selectedMoneyToggleOption = it }
            )


            //Graph

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