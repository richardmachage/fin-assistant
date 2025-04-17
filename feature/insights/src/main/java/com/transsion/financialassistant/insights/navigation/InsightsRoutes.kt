package com.transsion.financialassistant.insights.navigation

import com.transsion.financialassistant.data.models.TransactionCategory
import kotlinx.serialization.Serializable

@Serializable
sealed interface InsightsRoutes {

    @Serializable
    data object Insights : InsightsRoutes

    @Serializable
    data class CategoryInsights(
        val category: String? = null,
        val startDate: String? = null,
        val endDate: String? = null,
        val timeLine: String? = null,
        val transactionCategory: TransactionCategory? = null
    ) : InsightsRoutes

}