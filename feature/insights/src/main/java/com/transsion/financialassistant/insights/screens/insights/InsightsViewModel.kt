package com.transsion.financialassistant.insights.screens.insights

import androidx.lifecycle.ViewModel
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.insights.model.InsightCategory
import com.transsion.financialassistant.insights.model.InsightCategoryCardItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class InsightsViewModel @Inject constructor() : ViewModel() {
    private var _state = MutableStateFlow(InsightsScreenState())
    val state = _state.asStateFlow()


    fun switchTransactionCategory(transactionCategory: TransactionCategory) {
        _state.update { it.copy(transactionCategory = transactionCategory) }
    }

    fun switchInsightCategory(insightCategory: InsightCategory) {
        _state.update { it.copy(insightCategory = insightCategory) }
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


