package com.transsion.financialassistant.onboarding.screens.create_pin

// Pin State Handling
//sealed class PinState {
//    data object Idle : PinState()
//    data object Loading : PinState()
//    data object Success : PinState()
//
//    data class Error(val message: String) : PinState()
//
//
//}

data class CreatePinScreenState(
    var isIdle: Boolean = true,
    var isLoading : Boolean = false,
    var success : Boolean = false,
    var toastMessage : String? = null,
    var error : String? = null,
)