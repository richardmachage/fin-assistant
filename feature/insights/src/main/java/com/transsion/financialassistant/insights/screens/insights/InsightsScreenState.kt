package com.transsion.financialassistant.insights.screens.insights

import com.transsion.financialassistant.data.models.InsightCategory
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.insights.model.InsightTimeline

data class InsightsScreenState(
    val isLoading: Boolean = false,
    val toastMessage: String? = null,
    val transactionCategory: TransactionCategory = TransactionCategory.IN,
    val insightCategory: InsightCategory = InsightCategory.PERSONAL,
    val insightTimeline: InsightTimeline = InsightTimeline.TODAY,

    val moneyIn: String? = null,
    val moneyOut: String? = null,
    val transactionsIn: String? = null,
    val transactionsOut: String? = null,
    val totalTransactionCost: String? = null
)
