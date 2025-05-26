package com.transsion.financialassistant.search.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface SearchRoutes {
    @Serializable
    data object Search : SearchRoutes
}