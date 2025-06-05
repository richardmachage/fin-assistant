package com.transsion.financialassistant.home.screens.all_transactions

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.wear.compose.material.Icon
import com.transsion.financialassistant.data.repository.getMessageForTransaction
import com.transsion.financialassistant.data.room.views.personal.UnifiedTransactionPersonal
import com.transsion.financialassistant.data.utils.toAppTime
import com.transsion.financialassistant.data.utils.toMonthDayDate
import com.transsion.financialassistant.home.R
import com.transsion.financialassistant.home.model.TransactionUi
import com.transsion.financialassistant.home.screens.all_transactions.filter.FilterState
import com.transsion.financialassistant.home.screens.all_transactions.filter.TransactionFilterDialog
import com.transsion.financialassistant.home.screens.all_transactions.filter.isFilterEmpty
import com.transsion.financialassistant.home.screens.components.InOutCard
import com.transsion.financialassistant.home.screens.components.InsightCateToggleSegmentedButton
import com.transsion.financialassistant.home.screens.components.TransactionUiListItem
import com.transsion.financialassistant.presentation.components.bottom_sheets.BottomSheetFa
import com.transsion.financialassistant.presentation.components.buttons.IconButtonFa
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.components.texts.TitleText
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingLarge
import com.transsion.financialassistant.presentation.utils.paddingMedium
import com.transsion.financialassistant.presentation.utils.paddingSmall
import com.transsion.financialassistant.search.navigation.SearchRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllTransactionsScreen(
    navController: NavController,
    viewModel: AllTransactionsViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val filterResults = viewModel.filterResults.collectAsLazyPagingItems()
    var screenView by remember { mutableStateOf(ScreenView.DEFAULT) }
    val hideBalance = viewModel.hideBalance.collectAsState(false)
    var showMessageBottomSheet by remember { mutableStateOf(false) }
    var selectedMessage by remember { mutableStateOf("") }
    var selectedTransaction by remember { mutableStateOf<UnifiedTransactionPersonal?>(null) }



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
                            navController.navigate(SearchRoutes.Search)
                        }
                    )
                }
            )
        }
    ) { paddingValues ->

        // Filter Dialog
        if (showDialog) {
            TransactionFilterDialog(
                filter = viewModel.filters.collectAsState().value,
                onDismiss = { showDialog = false },
                onApply = { filterState ->
                    // Use the applied filter state
                    Log.d("AllTransactionsScreen", "filter : $filterState")
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
            AnimatedVisibility(visible = viewModel.filters.collectAsState().value.isFilterEmpty()) {
                Column(
                ) {
                    InOutCard(
                        moneyIn = state.moneyIn ?: "0.0",
                        moneyOut = state.moneyOut ?: "0.0",
                        transactionsIn = state.transactionsIn ?: "0",
                        transactionsOut = state.transactionsOut ?: "0",
                        onHideBalance = {
                            viewModel.onHideBalance(
                                hideBalance.value.not()
                            )
                        },
                        hide = hideBalance.value
                    )

                    VerticalSpacer(8)
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth()
                    )
                    VerticalSpacer(8)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {


                AnimatedContent(targetState = viewModel.filters.collectAsState()) { filters ->
                    when (filters.value.isFilterEmpty()) {
                        true -> {
                            // var selectedCategory by remember { mutableStateOf(InsightCategory.PERSONAL) }

                            InsightCateToggleSegmentedButton(
                                modifier = Modifier.fillMaxWidth(0.8f),
                                selectedOption = state.insightCategory,
                                onOptionSelected = {
                                    viewModel.onInsightCategoryChange(it)
                                }
                            )
                        }

                        false -> {
                            Row {

                                filters.value.source?.let {
                                    //UI
                                    FilterItem(name = it.description)
                                }

                                filters.value.period?.let {
                                    FilterItem(name = it.label)
                                }

                            }

                        }
                    }

                }


                Row {
                    if (viewModel.filters.collectAsState().value.isFilterEmpty()
                            .not()
                    ) {
                        IconButtonFa(
                            icon = Icons.Default.Clear,
                            colors = IconButtonColors(
                                containerColor = FAColors.lightGreen,
                                contentColor = Color.Black,
                                disabledContainerColor = Color.Transparent,
                                disabledContentColor = Color.Transparent
                            ),
                            onClick = {
                                viewModel.onChangeFilters(filterState = FilterState())
                            }
                        )
                    }

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
            }
            VerticalSpacer(16)
            VerticalSpacer(8)

            if (
                filterResults.itemCount == 0
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    NormalText(text = stringResource(R.string.no_data_available))
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingMedium),
                )
                {

                    items(filterResults.itemCount) { index ->
                        val item = filterResults[index]
                        if (item != null) {
                            //VerticalSpacer(5)
                            TransactionUiListItem(
                                transactionUi = TransactionUi(
                                    title = item.name ?: item.transactionCode,
                                    type = item.transactionType,
                                    amount = item.amount.toString(),
                                    inOrOut = item.transactionCategory,
                                    dateAndTime = "${item.date.toMonthDayDate()}, ${item.time.toAppTime()}"
                                ),
                                onClick = {
                                    getMessageForTransaction(
                                        context = context,
                                        transactionCode = item.transactionCode
                                    )
                                        .apply {
                                            onSuccess { message ->
                                                selectedTransaction = item
                                                selectedMessage = message
                                                showMessageBottomSheet = true

                                            }

                                            onFailure { error ->
                                                Toast.makeText(
                                                    context,
                                                    error.message,
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                }
                            )
                            //VerticalSpacer(5)
                            /*HorizontalDivider(
                                modifier = Modifier.padding(bottom = paddingSmall),
                                thickness = (0.25).dp
                            )*/
                        }

                    }
                }
            }
        }


        BottomSheetFa(
            isSheetOpen = showMessageBottomSheet,
            onDismiss = {
                showMessageBottomSheet = false
                selectedMessage = ""
            }
        ) {
            if (selectedMessage.isNotBlank()) {
                Column(
                    modifier = Modifier
                        .padding(start = paddingLarge, end = paddingLarge)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {

                    selectedTransaction?.let { transaction ->

                        //tittle -> Transaction type
                        TitleText(
                            text = transaction.transactionType.description,
                        )
                        VerticalSpacer(20)


                        //Message
                        NormalText(
                            text = selectedMessage,
                            textAlign = TextAlign.Left
                        )
                        VerticalSpacer(10)
                        /* if (transaction.transactionType == TransactionType.SEND_MONEY) {
                             OutlineButtonFa(
                                 text = "Reverse Transaction",
                                 onClick = {
                                     //TODO
                                     Toast.makeText(context, "Coming soon...", Toast.LENGTH_SHORT)
                                         .show()
                                 }
                             )
                         }*/
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


@Composable
private fun FilterItem(name: String, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .padding(paddingSmall)
            .background(color = FAColors.lightGreen, shape = RoundedCornerShape(50)),
        contentAlignment = Alignment.Center

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            NormalText(
                modifier = Modifier.padding(paddingMedium),
                text = name,
                fontSize = 16.sp,
                textColor = Color.Black
            )
        }
    }
}


private enum class ScreenView {
    SEARCH,
    DEFAULT
}