package com.transsion.financialassistant.onboarding.screens.login

data class LoginScreenState(
    var isLoading : Boolean = false,
    var toastMessage : String? = null,
    var pin : String = "",

)
