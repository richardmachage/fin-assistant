package com.transsion.financialassistant.main_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.transsion.financialassistant.onboarding.OnBoarding
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinancialAssistantTheme {
                OnBoarding()
            }
        }
    }
}
