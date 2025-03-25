package com.transsion.financialassistant.onboarding.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transsion.financialassistant.onboarding.R
import com.transsion.financialassistant.onboarding.domain.OnboardingRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
) : ViewModel() {
    private var _state = MutableStateFlow(LoginScreenState())
//    private val _loginState = MutableStateFlow<PinState>(PinState.Idle)
//    val loginState: StateFlow<PinState> = _loginState
    val state = _state.asStateFlow()

    private val _pin = MutableStateFlow("")
    val pin: StateFlow<String> = _pin
    var confirmnPin = mutableStateOf("")
    private val _errorMessage = MutableStateFlow<String?>(null)
    var errorMessage: StateFlow<String?> = _errorMessage


    fun onPinEntered(digit: String) {
        if(_pin.value.length < 4){
            _pin.value += digit

            // if user has entered 4 digit pin, verify the PIN
            if (_pin.value.length == 4) {
                validatePin(_pin.value)
            }
        }
    }

    fun clearPin() {
        _pin.value = ""
    }

    fun onPinChange(digit:String){
        if (state.value.pin.length < 4) _state.update { it.copy(pin = _state.value.pin + digit) }
    }

    fun validatePin(pin: String){
       viewModelScope.launch {
           //_loginState.value = PinState.Loading
           _state.value = state.value.copy(isLoading = true)

           loginRepo.verifyPin(
               pin = pin,
               onSuccess = { isCorrect ->
                   if (isCorrect) {
                      // _loginState.value = PinState.Success
                       _state.value = state.value.copy(isSuccess = true)
                       _errorMessage.value = null // Clear PIN input on failure
                   } else {
                       //_loginState.value = PinState.Error("Incorrect PIN")
                       _state.value = state.value.copy(toastMessage = "Incorrect PIN")
                       _errorMessage.value = "Incorrect PIN. Try again."
                       _pin.value = "" // Clear PIN input on failure
                   }
               },
               onFailure = {errorMessage ->
                   //_loginState.value = PinState.Error(errorMessage)
                   _state.value = state.value.copy(toastMessage = errorMessage)
                   _errorMessage.value = errorMessage
                   _pin.value = "" // Clear PIN input on failure
               }
           )
       }
    }


    fun onLogin(
        onSuccess: () -> Unit,
    ) {
        //TODO log n functionality
        viewModelScope.launch {
            toggleLoading(true)
            state.value.pin = ""
            delay(3000)
            onSuccess()
            toggleLoading(false)
            // showToast("Logged In successfully")

        }
    }
    fun getGreetingBasedOnTime(): Int {
        val currentHour = LocalTime.now().hour
        return when (currentHour) {
            in 5..11 -> R.string.good_morning
            in 12..16 -> R.string.good_afternoon
            in 17..20 -> R.string.good_evening
            else -> R.string.good_night
        }
    }

    fun showToast(message:String){
        _state.update { it.copy(toastMessage = message) }
    }

    fun resetToast(){
        _state.update { it.copy(toastMessage = null) }
    }

    fun toggleLoading(isLoading:Boolean){
        _state.update { it.copy(isLoading=isLoading) }
    }

    fun onBackSpace() {
        _state.update { it.copy(pin = it.pin.dropLast(1)) }
    }

}