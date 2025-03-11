package com.transsion.financialassistant.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.transsion.financialassistant.onboarding.screens.confirm_number.ConfirmNumberScreen
import com.transsion.financialassistant.onboarding.screens.create_pin.CreatePinScreen
import com.transsion.financialassistant.onboarding.screens.get_started.GetStarted

fun NavGraphBuilder.onboardingNavGraph(
    navController: NavController
) {
    composable<OnboardingRoutes.Welcome> {
        GetStarted(
            navController = navController
        )
    }

    composable<OnboardingRoutes.ConfirmNumber> {
        ConfirmNumberScreen(navController = navController)
    }

    composable<OnboardingRoutes.CreatePin> {
        CreatePinScreen()
    }

}