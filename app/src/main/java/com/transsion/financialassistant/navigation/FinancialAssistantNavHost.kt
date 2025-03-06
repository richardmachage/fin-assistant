package com.transsion.financialassistant.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.transsion.financialassistant.onboarding.navigation.OnboardingRoutes
import com.transsion.financialassistant.onboarding.navigation.onboardingNavGraph

@Composable
fun FinancialAssistantNavHost(
    navController: NavHostController,
    startDestination: OnboardingRoutes,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        onboardingNavGraph(
            navController = navController
        )
    }

}