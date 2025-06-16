package com.transsion.financialassistant.insights.screens.category_insights

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.transsion.financialassistant.data.repository.getMessageForTransaction
import com.transsion.financialassistant.data.utils.toAppTime
import com.transsion.financialassistant.data.utils.toMonthDayDate
import com.transsion.financialassistant.insights.model.InsightTimeline
import com.transsion.financialassistant.insights.model.TransactionUi
import com.transsion.financialassistant.insights.screens.components.BottomSheetFaMessage
import com.transsion.financialassistant.insights.screens.components.Graph
import com.transsion.financialassistant.insights.screens.components.TransactionUiListItem
import com.transsion.financialassistant.presentation.R
import com.transsion.financialassistant.presentation.components.texts.TitleText
import com.transsion.financialassistant.presentation.utils.paddingMedium
import com.transsion.financialassistant.presentation.utils.paddingSmall

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryInsightsScreen(
    navController: NavController,
    viewModel: CategoryInsightsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val categoryGraphData by if (viewModel.category == "transactionCost") viewModel.transactionCostGraphData.collectAsStateWithLifecycle() else viewModel.categoryGraphData.collectAsStateWithLifecycle()
    val listOfTransactions by if (viewModel.category == "transactionCost") viewModel.listOfTransactionCosts.collectAsStateWithLifecycle() else viewModel.listOfTransactions.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var showMessageBottomSheet by remember { mutableStateOf(false) }
    var selectedMessage by remember { mutableStateOf("") }
    var selectedTransaction by remember { mutableStateOf<TransactionUi?>(null) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TitleText(text = if (viewModel.category == "transactionCost") stringResource(R.string.transaction_costs) else viewModel.category)
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Graph(
                modifier = Modifier.padding(paddingMedium),
                title = "",
                subtitle = viewModel.timeLine,
                dataPoints = categoryGraphData.reversed(),
                bottomValueFormatter = { value ->
                    when (viewModel.insightTimeline) {
                        InsightTimeline.TODAY -> value.toAppTime()
                        else -> value.toMonthDayDate()
                    }
                }
            )

            TitleText(text = stringResource(R.string.transactions_))

            LazyColumn(
                modifier = Modifier.padding(paddingSmall)
            ) {
                items(listOfTransactions.reversed()) {
                    TransactionUiListItem(
                        transactionUi = it,
                        onClick = {
                            getMessageForTransaction(
                                context = context,
                                transactionCode = it.code
                            )
                                .apply {
                                    onSuccess { message ->
                                        selectedTransaction = it
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
                }
            }
        }


        selectedTransaction?.let {
            BottomSheetFaMessage(
                modifier = Modifier,
                showMessageBottomSheet = showMessageBottomSheet,
                selectedMessage = selectedMessage,
                onDismiss = {
                    showMessageBottomSheet = false
                    selectedMessage = ""
                    selectedTransaction = null
                },
                transaction = it
            )
        }

    }

}