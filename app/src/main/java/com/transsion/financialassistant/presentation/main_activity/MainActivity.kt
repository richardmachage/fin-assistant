package com.transsion.financialassistant.presentation.main_activity

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.transsion.financialassistant.presentation.navigation.FinancialAssistantNavHost
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<MainViewModel>()

        installSplashScreen()

        enableEdgeToEdge()
        setContent {

            FinancialAssistantTheme {
                val financialAssistantController = rememberNavController()
                FinancialAssistantNavHost(
                    navController = financialAssistantController,
                    startDestination = viewModel.getStartDestination()/*FinancialAssistantRoutes.Landing*/ //InsightsRoutes.Insights //viewmodel.getStartDestination()//OnboardingRoutes.Welcome
                )
            }
        }
    }
}
