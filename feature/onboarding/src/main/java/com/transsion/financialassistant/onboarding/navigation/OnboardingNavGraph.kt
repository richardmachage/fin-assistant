package com.transsion.financialassistant.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.transsion.financialassistant.onboarding.screens.welcome.WelcomeScreen

fun NavGraphBuilder.onboardingNavGraph(
    navController: NavController
) {
    composable<OnboardingRoutes.Welcome> {
        WelcomeScreen()
    }

}