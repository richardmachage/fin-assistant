package com.transsion.financialassistant.onboarding.screens.create_pin

// Pin State Handling
sealed class PinState {
    data object Idle : PinState()
    data object Loading : PinState()
    data object Success : PinState()

    data class Error(val message: String) : PinState()
}