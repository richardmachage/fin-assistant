package com.transsion.financialassistant.settings.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface SettingRoutes {

    @Serializable
    data object Settings : SettingRoutes

}