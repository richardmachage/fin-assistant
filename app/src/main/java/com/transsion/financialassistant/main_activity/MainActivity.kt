package com.transsion.financialassistant.main_activity

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.compose.rememberNavController
import com.transsion.financialassistant.navigation.FinancialAssistantNavHost
import com.transsion.financialassistant.onboarding.navigation.OnboardingRoutes
import com.transsion.financialassistant.permissions.requestSmsPermissions
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            requestSmsPermissions(
                context = this,
                requestPermissionLauncher = requestPermissionLauncher
            ) // Request permissions when the activity starts
            FinancialAssistantTheme {
                val financialAssistantController = rememberNavController()
                FinancialAssistantNavHost(
                    navController = financialAssistantController,
                    startDestination = OnboardingRoutes.Welcome
                )
            }
        }
    }

    //FIXME rewrite this function to factor proper permission handling with app dialogs
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val grantedPermissions = permissions.entries.filter { it.value }.map { it.key }
            if (grantedPermissions.containsAll(
                    listOf(Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS)
                )
            ) {
                Toast.makeText(this, "Read and Send SMS permissions granted", Toast.LENGTH_SHORT)
                    .show()
                // Process the messages (e.g., display them, use them for financial analysis)

            } else {
                // Handle the case where one or both permissions are denied.
                val deniedPermissions = permissions.entries.filter { !it.value }.map { it.key }
                Toast.makeText(this, "Permissions denied: $deniedPermissions", Toast.LENGTH_SHORT)
                    .show()
                // Provide user feedback or disable features that rely on SMS access.
            }
        }


}