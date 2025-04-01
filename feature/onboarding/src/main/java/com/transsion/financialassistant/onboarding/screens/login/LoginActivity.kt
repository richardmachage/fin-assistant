package com.transsion.financialassistant.onboarding.screens.login

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.rememberNavController
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // splash screen to avoid flickering when navigating to the next page
        installSplashScreen()

        enableEdgeToEdge()

        // Set content for the activity with Jetpack Compose
        setContent {
            // Use the composable function to set the UI content
            FinancialAssistantTheme {
                // Create a NavController inside the composable context
                val navController = rememberNavController()
                LoginScreen(
                    navController = navController
                )
            }
        }
    }
}

