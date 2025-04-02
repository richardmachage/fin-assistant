package com.transsion.financialassistant.data.biometric

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class BiometricPromptManager (
    // For Biometric functionality the class has to extend Activity
    private val activity: AppCompatActivity
) {
    private val resultChannel = Channel<BiometricResult>()
    val promptResult = resultChannel.receiveAsFlow()

    // Biometric Prompt
    @RequiresApi(Build.VERSION_CODES.P)
    fun showBiometricPrompt(
        title: String,
        description: String
    ){
        // Defining the Biometric Prompt
        val manager = BiometricManager.from(activity)
        val authenticators = if (Build.VERSION.SDK_INT >= 30) {
            BiometricManager.Authenticators.BIOMETRIC_STRONG //or
                    //BiometricManager.Authenticators.DEVICE_CREDENTIAL
        } else
            BiometricManager.Authenticators.BIOMETRIC_STRONG

        val promptInfo = PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(description)
            .setAllowedAuthenticators(authenticators)
            .setConfirmationRequired(false)

        // check if the device meets biometric hardware requirements - From Android 11 and above
        if (Build.VERSION.SDK_INT < 30){
            promptInfo.setNegativeButtonText("Cancel")
        }

        when(manager.canAuthenticate(authenticators)) {
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                resultChannel.trySend(BiometricResult.HardwareUnavailable)
                return
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                resultChannel.trySend(BiometricResult.FeatureUnavailable)
                return
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                resultChannel.trySend(BiometricResult.AuthenticationNotSet)
                return
            }
            else -> Unit
        }

        val prompt = BiometricPrompt(
            activity, // AppCompatActivity instance
            ContextCompat.getMainExecutor(activity),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    resultChannel.trySend(BiometricResult.AuthenticationError(errString.toString()))
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    resultChannel.trySend(BiometricResult.AuthenticationSuccess)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    resultChannel.trySend(BiometricResult.AuthenticationFailed)
                }
            }
        )

        // Call the biometric prompt
        prompt.authenticate(promptInfo.build())
    }
}