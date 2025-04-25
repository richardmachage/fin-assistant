package com.transsion.financialassistant.insights.screens.category_insights

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.transsion.financialassistant.data.utils.toMonthDayDate
import com.transsion.financialassistant.insights.screens.components.Graph
import com.transsion.financialassistant.insights.screens.components.TransactionUiListItem
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
    val categoryGraphData by viewModel.categoryGraphData.collectAsStateWithLifecycle()
    val listOfTransactions by viewModel.listOfTransactions.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TitleText(text = viewModel.category)
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
                    value.toMonthDayDate()
                }
            )

            TitleText(text = stringResource(com.transsion.financialassistant.presentation.R.string.transactions_))

            LazyColumn(
                modifier = Modifier.padding(paddingSmall)
            ) {
                items(listOfTransactions.reversed()) {
                    TransactionUiListItem(transactionUi = it)
                }
            }
        }

    }

}