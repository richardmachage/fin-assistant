package com.transsion.financialassistant.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.transsion.financialassistant.home.navigation.homeNavGraph
import com.transsion.financialassistant.insights.navigation.insightsNavGraph
import com.transsion.financialassistant.onboarding.navigation.onboardingNavGraph
import com.transsion.financialassistant.presentation.landing_screen.LandingScreen

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
            navController = navController,
            goToLanding = { route ->
                navController.navigate(route = FinancialAssistantRoutes.Landing) {
                    popUpTo(route = route) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            },
        )

        insightsNavGraph(navController = navController)

        homeNavGraph(navController = navController)

        composable<FinancialAssistantRoutes.Landing> {
            LandingScreen(
                mainNavController = navController
            )
        }
    }

}