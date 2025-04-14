package com.transsion.financialassistant.home.screens.home

import com.transsion.financialassistant.data.models.InsightCategory
import com.transsion.financialassistant.home.model.RecentsUI

data class HomeScreenState(
    val isLoading: Boolean = false,
    val toastMessage: String? = null,
    val insightCategory: InsightCategory = InsightCategory.PERSONAL,
    val recentTransactions: List<RecentsUI> = emptyList(),
    val moneyIn: String = "0.0",
    val moneyOut: String = ".0",
)
