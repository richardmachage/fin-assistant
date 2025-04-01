package com.transsion.financialassistant.onboarding.screens.login

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.security.crypto.EncryptedSharedPreferences
import com.transsion.financialassistant.data.preferences.SharedPreferences.Companion.PIN_KEY
import com.transsion.financialassistant.onboarding.R
import com.transsion.financialassistant.onboarding.domain.OnboardingRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepo: OnboardingRepo,
    @ApplicationContext private val context: Context
) : ViewModel() {
    // Login screen state containing all UI-related data
    private var _state = MutableStateFlow(LoginScreenState())
    val state: StateFlow<LoginScreenState> = _state.asStateFlow()

    private val _pin = MutableStateFlow("")
    val pin: StateFlow<String> = _pin

    private val _errorMessage = MutableStateFlow<String?>(null)
    var errorMessage: StateFlow<String?> = _errorMessage

    private val sharedPreferences = EncryptedSharedPreferences.create(
        "secure_prefs", // File name for encrypted preferences
        getOrCreateKey().toString(), // Key for encryption
        context, // Application context
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, // Encryption scheme for the key
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM // Encryption scheme for the value
    )

    // Handle pin entry action
    fun onPinEntered(digit: String) {
        if (_pin.value.length < 4) {
            _pin.value += digit
            if (_pin.value.length == 4) {
                validatePin(_pin.value)
            }
        }
    }

    fun setPin(pin: String) {
        _pin.value = pin // to set the pin value from outside the ViewModel
    }

    // Clear PIN input
    fun clearPin() {
        _pin.value = ""
    }

    // reset error message
    fun resetErrorMessage() {
        _errorMessage.value = null
    }

    // Retrieve the saved PIN
    fun getPin(): String? {
        return sharedPreferences.getString(PIN_KEY, null)
    }

    // Handle pin validation
    fun validatePin(pin: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            loginRepo.verifyPin(pin = pin,
                onSuccess = { isCorrect ->
                    if (isCorrect) {
                        _state.update { it.copy(isSuccess = true, isLoading = false, toastMessage = null) }
                    } else {
                        _state.update { it.copy(isSuccess = false, isLoading = false, toastMessage = "Incorrect PIN") }
                        _pin.value = "" // Reset PIN on failure
                    }
                },
                onFailure = { errorMessage ->
                    _state.update { it.copy(isLoading = false, toastMessage = errorMessage) }
                    _pin.value = "" // Reset PIN on failure
                }
            )
        }
    }

    fun onPinChange(digit:String){
        if (state.value.pin.length < 4) _state.update { it.copy(pin = _state.value.pin + digit) }
    }

    // Handle the login process
    fun onLogin(onSuccess: () -> Unit) {
        viewModelScope.launch {
            toggleLoading(true)
            delay(3000) // Simulate login delay
            onSuccess()
            toggleLoading(false)
        }
    }

    // Get appropriate greeting based on the time of day
    fun getGreetingBasedOnTime(): Int {
        val currentHour = LocalTime.now().hour
        return when (currentHour) {
            in 5..11 -> R.string.good_morning
            in 12..16 -> R.string.good_afternoon
            in 17..20 -> R.string.good_evening
            else -> R.string.good_night
        }
    }

    // Show a toast message (UI update)
    fun showToast(message: String) {
        _state.update { it.copy(toastMessage = message) }
    }

    // Reset the toast message state
    fun resetToast() {
        _state.update { it.copy(toastMessage = null) }
    }

    // Toggle loading state
    fun toggleLoading(isLoading: Boolean) {
        _state.update { it.copy(isLoading = isLoading) }
    }

    // Handle backspace key for PIN entry
    fun onBackSpace() {
        _state.update { it.copy(pin = it.pin.dropLast(1)) }
    }

    // Generate or get the master encryption key from Android Keystore
    private fun getOrCreateKey(): SecretKey {
        val keyStore = java.security.KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
        val key = keyStore.getKey("pin_encryption_key", null)
        if (key != null) return key as SecretKey

        // If key doesn't exist, generate a new key
        val keyGenerator = KeyGenerator.getInstance("AES", "AndroidKeyStore").apply {
            init(
                KeyGenParameterSpec.Builder("pin_encryption_key", KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .build()
            )
        }
        return keyGenerator.generateKey()
    }
}
