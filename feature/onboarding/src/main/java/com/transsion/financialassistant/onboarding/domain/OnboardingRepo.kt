package com.transsion.financialassistant.onboarding.domain

import android.content.Context

interface OnboardingRepo {

    /**Checks if the user has completed onboarding.*/
    fun hasCompletedOnboarding(): Boolean?


    /** Saves the user's onboarding status.*/
    fun setCompletedOnboarding()

    /** Get the mpesa numbers on the device */
    fun getMpesaNumbersOnDevice(
        context: Context,
        onSuccess: (phoneNumbers: List<String>) -> Unit,
        onFailure: (errorMessage: String) -> Unit
    )




}