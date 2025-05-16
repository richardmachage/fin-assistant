package com.transsion.financialassistant.admin.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AdminRoutes {

    @Serializable
    data object Home : AdminRoutes

    @Serializable
    data class MoreDetails(
        val title: String,
        val description: String,
        val isSolved: Boolean,
        val screenShotUrl: String?
    ) : AdminRoutes
}