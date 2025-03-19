package com.transsion.financialassistant.onboarding.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transsion.financialassistant.onboarding.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private var _state = MutableStateFlow(LoginScreenState())
    val state = _state.asStateFlow()

    var pin = mutableStateOf("")
    var confirmnPin = mutableStateOf("")
    var errorMessage = mutableStateOf<String?>(null)

    fun validatePin(){
        if (pin.value.length < 4){
            errorMessage.value = "Pin Must be atleast 4 Digits"
            return
        }

        if (pin.value != confirmnPin.value){
            errorMessage.value = "Pins do not match"
            return
        }
    }

    fun onPinChange(digit:String){
        if (state.value.pin.length < 4) _state.update { it.copy(pin = _state.value.pin + digit) }
    }

    fun onLogin(){
       //TODO log n functionality
        viewModelScope.launch {
            toggleLoading(true)
            delay(3000)
            toggleLoading(false)
            showToast("Logged In successfully")
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