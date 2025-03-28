package com.transsion.financialassistant.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.transsion.financialassistant.onboarding.navigation.OnboardingRoutes
import com.transsion.financialassistant.onboarding.navigation.onboardingNavGraph

@RequiresApi(Build.VERSION_CODES.Q)
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