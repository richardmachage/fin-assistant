package com.transsion.financialassistant.admin.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.transsion.financialassistant.admin.navigation.AdminRoutes
import com.transsion.financialassistant.admin.navigation.adminNavGraph
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            FinancialAssistantTheme {
                NavHost(
                    navController = navController,
                    startDestination = AdminRoutes.Home
                ) {
                    adminNavGraph(navController)
                }
            }
        }
    }
}

