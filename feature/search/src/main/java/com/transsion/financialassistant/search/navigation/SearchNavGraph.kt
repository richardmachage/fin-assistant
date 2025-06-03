package com.transsion.financialassistant.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.transsion.financialassistant.search.screens.search.SearchScreen

fun NavGraphBuilder.searchNavGraph(
    navController: NavController,

    ) {
    composable<SearchRoutes.Search> {
        SearchScreen(
            navController = navController
        )
    }
}