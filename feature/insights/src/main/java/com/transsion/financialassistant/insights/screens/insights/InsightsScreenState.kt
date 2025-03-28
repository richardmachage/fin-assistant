package com.transsion.financialassistant.insights.screens.insights

import com.transsion.financialassistant.data.models.TransactionCategory

data class InsightsScreenState(
    val isLoading: Boolean = false,
    val toastMessage: String? = null,
    val currentView: TransactionCategory = TransactionCategory.IN
)
