package com.transsion.financialassistant.presentation.main_activity

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.transsion.financialassistant.presentation.navigation.FinancialAssistantNavHost
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme
import com.transsion.financialassistant.settings.screens.settings.ThemeMode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        installSplashScreen()
            .setKeepOnScreenCondition {
                false
            }
        enableEdgeToEdge()
        setContent {
            val viewmodel = hiltViewModel<MainViewModel>()
            val themeMode by viewmodel.currentTheme.collectAsState()

            val darkTheme = when (themeMode) {
                ThemeMode.LIGHT.name -> false
                ThemeMode.DARK.name -> true
                else -> isSystemInDarkTheme()
            }

            FinancialAssistantTheme(
                darkTheme = darkTheme
            ) {
                val financialAssistantController = rememberNavController()
                FinancialAssistantNavHost(
                    navController = financialAssistantController,
                    startDestination = viewmodel.getStartDestination()/*FinancialAssistantRoutes.Landing*/ //InsightsRoutes.Insights //viewmodel.getStartDestination()//OnboardingRoutes.Welcome
                )
            }
        }
    }

}
