package com.transsion.financialassistant.presentation.landing_screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.transsion.financialassistant.R
import com.transsion.financialassistant.home.navigation.HomeRoutes
import com.transsion.financialassistant.home.screens.home.HomeScreen
import com.transsion.financialassistant.insights.navigation.InsightsRoutes
import com.transsion.financialassistant.insights.screens.insights.InsightsScreen
import com.transsion.financialassistant.presentation.components.bottom_nav_bar.BottomBarItem
import com.transsion.financialassistant.presentation.components.bottom_nav_bar.BottomNavBar

@Composable
fun LandingScreen(
    mainNavController: NavController,
    viewModel: LandingViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(
                modifier = Modifier.fillMaxWidth(),
                navController = navController,
                listOfBottomBarItems = listOf(
                    BottomBarItem(
                        route = HomeRoutes.Home,
                        title = R.string.home,
                        icon = com.transsion.financialassistant.presentation.R.drawable.home_11
                    ),
                    BottomBarItem(
                        route = InsightsRoutes.Insights,
                        title = com.transsion.financialassistant.insights.R.string.insights,
                        icon = com.transsion.financialassistant.presentation.R.drawable.chartpieslice
                    ),
                    /*BottomBarItem(
                        route = "More",
                        title = com.transsion.financialassistant.insights.R.string.more,
                        icon = com.transsion.financialassistant.presentation.R.drawable.more_01
                    )*/
                )
            )
        }
    ) { innerPadding ->

        NavHost(
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
            navController = navController,
            startDestination = HomeRoutes.Home
        ) {

            composable<InsightsRoutes.Insights>(
            ) {
                InsightsScreen(
                    navController = mainNavController
                )
            }

            composable<HomeRoutes.Home>(

            ) {
                HomeScreen(
                    navController = mainNavController
                )
            }

        }
    }
}