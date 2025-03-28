package com.transsion.financialassistant.insights.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface InsightsRoutes {

    @Serializable
    data object Insights : InsightsRoutes

    @Serializable
    data class CategoryInsights(val category: String? = null) : InsightsRoutes

}