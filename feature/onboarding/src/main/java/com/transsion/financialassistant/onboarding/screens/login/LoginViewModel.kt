package com.transsion.financialassistant.onboarding.screens.login

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transsion.financialassistant.onboarding.R
import com.transsion.financialassistant.onboarding.domain.OnboardingRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
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

    private val _biometricAuthenticated = MutableStateFlow(false)
    val biometricAuthenticated: StateFlow<Boolean> = _biometricAuthenticated

    private val _shouldTriggerBiometric = mutableStateOf(false)
    val shouldTriggerBiometric: State<Boolean> = _shouldTriggerBiometric



    // Handle pin entry action
    fun onPinEntered(digit: String) {
        if (_pin.value.length < 4) {
            _pin.value += digit
            if (_pin.value.length == 4) {
                validatePin(_pin.value)
            }
        }
    }

    /**Biometric Prompt Trigger*/
    fun resetBiometricTrigger() {
        _shouldTriggerBiometric.value = false
    }

    fun allowBiometricTrigger() {
        _shouldTriggerBiometric.value = true
    }


    // Clear PIN input
    fun clearPin() {
        _pin.value = ""
    }

    // reset validation success
    fun resetValidationSuccess() {
        _state.update { it.copy(isValidationSuccess = false) }
    }


    // reset error message
    fun resetErrorMessage() {
        _errorMessage.value = null
    }


    // Handle pin validation
    fun validatePin(pin: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            loginRepo.verifyPin(pin = pin,
                onSuccess = { isCorrect ->
                    if (isCorrect) {
                        _state.update {
                            it.copy(
                                isValidationSuccess = true,
                                isLoading = false,
                                toastMessage = null
                            )
                        }
                    } else {
                        _state.update {
                            it.copy(
                                isValidationSuccess = false,
                                isLoading = false,
                                toastMessage = "Incorrect PIN"
                            )
                        }
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
    /* fun onLogin(onSuccess: () -> Unit) {
         viewModelScope.launch {
             toggleLoading(true)
             //delay(3000) // Simulate login delay
             onSuccess()
             toggleLoading(false)
         }
     }*/

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


}
