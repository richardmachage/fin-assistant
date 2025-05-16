package com.transsion.financialassistant.onboarding.biometric

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class BiometricPromptManager (
    // For Biometric functionality the class has to extend Activity
    private val context: Context//AppCompatActivity
) : AppCompatActivity() {
    private val resultChannel = Channel<BiometricResult>()
    val promptResult = resultChannel.receiveAsFlow()

    // Biometric Prompt
    @RequiresApi(Build.VERSION_CODES.P)
    fun showBiometricPrompt(
        title: String,
        description: String
    ){
        // Defining the Biometric Prompt
        val manager = BiometricManager.from(context)

        /* use only Biometric */
        val authenticators = //BiometricManager.Authenticators.BIOMETRIC_STRONG
         /*  if (Build.VERSION.SDK_INT >= 30) {
            BiometricManager.Authenticators.BIOMETRIC_STRONG or
                   BiometricManager.Authenticators.BIOMETRIC_WEAK
        } else*/
            BiometricManager.Authenticators.BIOMETRIC_STRONG

        val promptInfoBuilder = PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(description)
            .setAllowedAuthenticators(authenticators)
            .setConfirmationRequired(false)

        // check if the device meets biometric hardware requirements - From Android 11 and above

        promptInfoBuilder.setNegativeButtonText("Cancel")
      /*  if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            promptInfoBuilder.setNegativeButtonText("Cancel")
        }*/

        val promptInfo = promptInfoBuilder.build()

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
            context as AppCompatActivity, // ComponentActivity instance
            ContextCompat.getMainExecutor(context),
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
        prompt.authenticate(promptInfo)
    }
}



