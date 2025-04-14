package com.transsion.financialassistant.home.screens.home

import com.transsion.financialassistant.data.models.InsightCategory
import com.transsion.financialassistant.data.room.db.UnifiedTransaction
import com.transsion.financialassistant.home.model.RecentsUI
import com.transsion.financialassistant.home.model.TransactionUi

data class HomeScreenState(
    val isLoading: Boolean = false,
    val toastMessage: String? = null,
    val insightCategory: InsightCategory = InsightCategory.PERSONAL,
    val recentTransactions: List<RecentsUI> = emptyList()
)
