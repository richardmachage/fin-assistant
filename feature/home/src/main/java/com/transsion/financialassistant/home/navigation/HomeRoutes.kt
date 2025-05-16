package com.transsion.financialassistant.home.navigation

import com.transsion.financialassistant.data.models.InsightCategory
import kotlinx.serialization.Serializable


@Serializable
sealed interface HomeRoutes {
    @Serializable
    data object Home : HomeRoutes

    @Serializable
    data object Search : HomeRoutes

    @Serializable
    data class AllTransactions(val insightCategory: InsightCategory) : HomeRoutes

}