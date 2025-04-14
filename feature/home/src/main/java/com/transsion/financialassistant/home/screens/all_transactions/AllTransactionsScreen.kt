package com.transsion.financialassistant.home.screens.all_transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.wear.compose.material.Icon
import com.transsion.financialassistant.data.models.InsightCategory
import com.transsion.financialassistant.data.utils.toAppTime
import com.transsion.financialassistant.data.utils.toMonthDayDate
import com.transsion.financialassistant.home.R
import com.transsion.financialassistant.home.model.TransactionUi
import com.transsion.financialassistant.home.screens.all_transactions.filter.TransactionFilterDialog
import com.transsion.financialassistant.home.screens.components.InOutCard
import com.transsion.financialassistant.home.screens.components.InsightCateToggleSegmentedButton
import com.transsion.financialassistant.home.screens.components.TransactionUiListItem
import com.transsion.financialassistant.presentation.components.buttons.IconButtonFa
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingMedium
import com.transsion.financialassistant.presentation.utils.paddingSmall

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllTransactionsScreen(
    navController: NavController,
    viewModel: AllTransactionsViewModel = hiltViewModel(),
) {
    var showDialog by remember { mutableStateOf(false) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val filterResults = viewModel.filterResults.collectAsLazyPagingItems()
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() },
                        modifier = Modifier.background(MaterialTheme.colorScheme.background),
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                title = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        BigTittleText(text = stringResource(R.string.all_transactions))

                    }
                },

                actions = {
                    //search
                    IconButtonFa(
                        icon = painterResource(id = com.transsion.financialassistant.presentation.R.drawable.search),
                        colors = colors(),
                        onClick = {
                            // TODO Handle Search
                        }
                    )
                }
            )
        }
    ) { paddingValues ->

        // Filter Dialog
        if (showDialog) {
            TransactionFilterDialog(
                onDismiss = { showDialog = false },
                onApply = { filterState ->
                    // Use the applied filter state
                    viewModel.onChangeFilters(filterState)
                }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                    start = paddingMedium,
                    end = paddingMedium
                )
        ) {
            // Money In/Out Transaction Card
            InOutCard(
                moneyIn = state.moneyIn ?: "0.0",
                moneyOut = state.moneyOut ?: "0.0",
                transactionsIn = state.transactionsIn ?: "0",
                transactionsOut = state.transactionsOut ?: "0"
            )

            VerticalSpacer(8)
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth()
            )
            VerticalSpacer(8)
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                var selectedCategory by remember { mutableStateOf(InsightCategory.PERSONAL) }
                InsightCateToggleSegmentedButton(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    selectedOption = selectedCategory,
                    onOptionSelected = {
                        selectedCategory = it
                    }
                )
                IconButtonFa(
                    icon = painterResource(id = com.transsion.financialassistant.presentation.R.drawable.vector__1_),
                    colors = IconButtonColors(
                        containerColor = FAColors.lightGreen,
                        contentColor = Color.Black,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.Transparent
                    ),
                    onClick = {
                        showDialog = true
                    }
                )
            }
            VerticalSpacer(16)


            VerticalSpacer(8)

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingMedium),
            ) {
                items(filterResults.itemCount) { index ->
                    val item = filterResults[index]
                    if (item != null) {
                        TransactionUiListItem(
                            transactionUi = TransactionUi(
                                title = item.name ?: item.transactionCode,
                                type = item.transactionType,
                                amount = item.amount.toString(),
                                inOrOut = item.transactionCategory,
                                dateAndTime = "${item.date.toMonthDayDate()}, ${item.time.toAppTime()}"
                            )
                        )
                        VerticalSpacer(5)
                        HorizontalDivider(modifier = Modifier.padding(bottom = paddingSmall))
                    }

                }


            }
        }
    }
}

@Composable
private fun colors() = IconButtonColors(
    containerColor = Color.Transparent,
    contentColor = MaterialTheme.colorScheme.onBackground,
    disabledContainerColor = Color.Transparent,
    disabledContentColor = Color.Transparent
)

@Preview(showBackground = true)
@Composable
fun AllTransactionScreenPreview() {
    FinancialAssistantTheme {
        AllTransactionsScreen(navController = rememberNavController())
    }
}