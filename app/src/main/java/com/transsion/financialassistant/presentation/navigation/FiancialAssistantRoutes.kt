package com.transsion.financialassistant.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface FinancialAssistantRoutes {
    @Serializable
    data object Landing : FinancialAssistantRoutes
}