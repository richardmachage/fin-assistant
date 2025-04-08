package com.transsion.financialassistant.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.transsion.financialassistant.home.screens.home.HomeScreen

fun NavGraphBuilder.homeNavGraph(
    navController: NavController
) {
    composable<HomeRoutes.Home> {
        HomeScreen(
            navController = navController
        )
    }
}