package com.transsion.financialassistant.insights.screens.insights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.insights.domain.InsightsRepo
import com.transsion.financialassistant.insights.model.InsightCategory
import com.transsion.financialassistant.insights.model.InsightCategoryCardItem
import com.transsion.financialassistant.insights.model.InsightTimeline
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsightsViewModel @Inject constructor(
    private val insightsRepo: InsightsRepo
) : ViewModel() {
    private var _state = MutableStateFlow(InsightsScreenState())
    val state = _state.asStateFlow()

    init {
        getMoneyIn()
        getNumberOfTransactionsIn()
    }

    fun getMoneyIn(
        startDate: String = "2023-01-01",
        endDate: String = "2023-12-31"
    ) {
        viewModelScope.launch {
            //so this one should fetch the data from the repo and update the state
            insightsRepo.getTotalMoneyIn(startDate, endDate).apply {
                onSuccess { totalMoneyIn ->
                    _state.update { it.copy(moneyIn = totalMoneyIn.toString()) }
                }

                onFailure {
                    //TODO
                }
            }
        }
    }

    fun getNumberOfTransactionsIn(
        startDate: String = "2023-01-01",
        endDate: String = "2023-12-31"
    ) {
        viewModelScope.launch {
            insightsRepo.getTransactionsNumOfIn(startDate, endDate).apply {
                onSuccess { numberOfTransactions ->
                    _state.update { it.copy(transactionsIn = numberOfTransactions.toString()) }
                }
                onFailure {
                    //TODO
                }
            }
        }

    }

    fun switchTransactionCategory(transactionCategory: TransactionCategory) {
        _state.update { it.copy(transactionCategory = transactionCategory) }
    }

    fun switchInsightCategory(insightCategory: InsightCategory) {
        _state.update { it.copy(insightCategory = insightCategory) }
    }

    fun switchInsightTimeline(insightTimeline: InsightTimeline) {
        _state.update { it.copy(insightTimeline = insightTimeline) }
    }


    val dummyInsightCategories = listOf(
        InsightCategoryCardItem(
            tittle = "Shopping",
            amount = "14,520.00",
            categoryIcon = com.transsion.financialassistant.presentation.R.drawable.weui_arrow_outlined
        ),
        InsightCategoryCardItem(
            tittle = "Food & Drinks",
            amount = "7,250.50",
            categoryIcon = com.transsion.financialassistant.presentation.R.drawable.cafe
        ),
        InsightCategoryCardItem(
            tittle = "Transport",
            amount = "3,860.00",
            categoryIcon = com.transsion.financialassistant.presentation.R.drawable.airplane_take_off_02
        ),
        InsightCategoryCardItem(
            tittle = "Entertainment",
            amount = "5,120.75",
            categoryIcon = com.transsion.financialassistant.presentation.R.drawable.calendar
        ),
        InsightCategoryCardItem(
            tittle = "Health",
            amount = "2,430.00",
            categoryIcon = com.transsion.financialassistant.presentation.R.drawable.dumbbell_01
        ),
        InsightCategoryCardItem(
            tittle = "Utilities",
            amount = "4,990.90",
            categoryIcon = com.transsion.financialassistant.presentation.R.drawable.add_square
        )
    )

}


