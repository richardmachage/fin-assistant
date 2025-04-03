package com.transsion.financialassistant.insights.screens.insights

import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.insights.model.InsightCategory

data class InsightsScreenState(
    val isLoading: Boolean = false,
    val toastMessage: String? = null,
    val transactionCategory: TransactionCategory = TransactionCategory.IN,
    val insightCategory: InsightCategory = InsightCategory.PERSONAL
)
