package com.transsion.financialassistant.home.screens.home

import com.transsion.financialassistant.data.models.InsightCategory

data class HomeScreenState(
    val isLoading: Boolean = false,
    val toastMessage: String? = null,
    val insightCategory: InsightCategory = InsightCategory.PERSONAL,
)
