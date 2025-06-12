package com.transsion.financialassistant.settings.screens.change_pin

data class ChangePinScreenState(
    val isLoading: Boolean = false,
    val oldPin: String = "",
    val newPin: String = "",
    val toastMessage: String? = null
)
