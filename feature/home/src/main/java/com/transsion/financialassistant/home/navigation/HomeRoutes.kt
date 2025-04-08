package com.transsion.financialassistant.home.navigation

sealed interface HomeRoutes {
    data object Home : HomeRoutes
    data object Search : HomeRoutes
    data object AllTransactions : HomeRoutes
}