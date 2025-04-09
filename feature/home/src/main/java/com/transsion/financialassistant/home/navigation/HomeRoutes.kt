package com.transsion.financialassistant.home.navigation

import kotlinx.serialization.Serializable


@Serializable
sealed interface HomeRoutes {
    @Serializable
    data object Home : HomeRoutes

    @Serializable
    data object Search : HomeRoutes

    @Serializable
    data object AllTransactions : HomeRoutes

}