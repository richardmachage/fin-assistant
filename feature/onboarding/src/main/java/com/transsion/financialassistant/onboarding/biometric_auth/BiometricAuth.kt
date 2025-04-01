package com.transsion.financialassistant.onboarding.biometric_auth

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.wear.compose.material.OutlinedButton
import com.transsion.financialassistant.data.biometric.BiometricPromptManager
import com.transsion.financialassistant.data.biometric.BiometricResult
import com.transsion.financialassistant.onboarding.R
import com.transsion.financialassistant.presentation.components.texts.NormalText

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun BiometricAuth (
    activity: AppCompatActivity,
    navController: NavController
) {
    val promptManager = remember{ BiometricPromptManager(activity) }
    val context = LocalContext.current
    val biometricResult by promptManager.promptResult.collectAsState(initial = null)

    val enrollLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            // Handle the result of the activity
            println("Activity result: $it")
        }
    )

    LaunchedEffect(biometricResult) {
        if (biometricResult is BiometricResult.AuthenticationNotSet) {
            if (Build.VERSION.SDK_INT >= 30) {
                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(
                        Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                    )
                }
                enrollLauncher.launch(enrollIntent)
            }
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedButton(
                onClick = {
                    promptManager.showBiometricPrompt(
                        title = context.getString(R.string.biometric_authentication),
                        description = context.getString(R.string.please_authenticate_to_continue)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "Authenticate")
            }
            biometricResult?.let { result ->
                NormalText(
                    text = when (result) {
                        is BiometricResult.AuthenticationError -> {
                            result.error
                        }

                        BiometricResult.AuthenticationFailed -> {
                            context.getString(R.string.authentication_failed)
                        }

                        BiometricResult.AuthenticationNotSet -> {
                            context.getString(R.string.authentication_not_set)
                        }

                        BiometricResult.AuthenticationSuccess -> {
                            context.getString(R.string.authentication_success)
                        }

                        BiometricResult.FeatureUnavailable -> {
                            context.getString(R.string.feature_unavailable)
                        }

                        BiometricResult.HardwareUnavailable -> {
                            context.getString(R.string.hardware_unavailable)
                        }
                    }
                )
            }
        }
    }
}