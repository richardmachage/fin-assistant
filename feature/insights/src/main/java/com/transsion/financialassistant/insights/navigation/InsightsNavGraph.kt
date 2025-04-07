package com.transsion.financialassistant.insights.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.transsion.financialassistant.insights.screens.category_insights.CategoryInsightsScreen
import com.transsion.financialassistant.insights.screens.insights.InsightsScreen


fun NavGraphBuilder.insightsNavGraph(
    navController: NavController
) {

    composable<InsightsRoutes.Insights> {
        InsightsScreen(
            navController = navController
        )
    }

    composable<InsightsRoutes.CategoryInsights> {
        CategoryInsightsScreen(
            navController = navController
        )

    }

}