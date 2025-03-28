package com.transsion.financialassistant.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.transsion.financialassistant.insights.navigation.insightsNavGraph
import com.transsion.financialassistant.onboarding.navigation.onboardingNavGraph

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun FinancialAssistantNavHost(
    navController: NavHostController,
    startDestination: Any,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        onboardingNavGraph(
            navController = navController
        )

        insightsNavGraph(
            navController = navController
        )
    }

}