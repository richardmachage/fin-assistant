package com.transsion.financialassistant.onboarding.screens.confirm_number

data class ConfirmNumberScreenState(
    val isLoading: Boolean = false,
    val toastMessage: String? = null,
    val mpesaNumbers: List<String> = emptyList(),
    val selectedNumber: String? = null,
    val errorMessage: String? = null
)
