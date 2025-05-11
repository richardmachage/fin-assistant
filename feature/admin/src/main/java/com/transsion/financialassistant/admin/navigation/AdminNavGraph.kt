package com.transsion.financialassistant.admin.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.transsion.financialassistant.admin.screens.home.HomeScreen
import com.transsion.financialassistant.admin.screens.more_details.MoreDetailsScreen

fun NavGraphBuilder.adminNavGraph(
    navController: NavController
) {
    composable<AdminRoutes.Home> {
        HomeScreen(navController)
    }

    composable<AdminRoutes.MoreDetails> {
        MoreDetailsScreen(navController)
    }
}