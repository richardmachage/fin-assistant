package com.transsion.financialassistant.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.transsion.financialassistant.feedback.navigation.feedbackNavGraph
import com.transsion.financialassistant.settings.screens.change_pin.ChangePinScreen
import com.transsion.financialassistant.settings.screens.settings.SettingsScreen

fun NavGraphBuilder.settingsNavGraph(
    navController: NavController,
) {
    composable<SettingRoutes.Settings> {
        SettingsScreen(
            navController = navController
        )
    }

    feedbackNavGraph(navController)

    composable<SettingRoutes.ChangePin> {
        ChangePinScreen(
            navController = navController
        )
    }
}