package com.transsion.financialassistant.onboarding.screens.create_pin

// Pin State Handling
sealed class PinState {
    object Idle : PinState()
    object Loading : PinState()
    object Success : PinState()

    data class Error(val message: String) : PinState()
}