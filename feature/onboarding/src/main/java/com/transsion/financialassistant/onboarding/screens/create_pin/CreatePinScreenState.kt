package com.transsion.financialassistant.onboarding.screens.create_pin


data class CreatePinScreenState(
    var isIdle: Boolean = true,
    var isLoading : Boolean = false,
    var success : Boolean = false,
    var hasPinCreated : Boolean = false,
    var toastMessage : String? = null,
    var error : String? = null,
)