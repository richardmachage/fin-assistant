package com.transsion.financialassistant.onboarding.domain

import android.content.Context

interface OnboardingRepo {

    /**Checks if the user has completed onboarding.*/
    fun hasCompletedOnboarding(): Boolean

    /** Saves the user's onboarding status.*/
    fun setCompletedOnboarding()

    /** Get the mpesa numbers on the device */
    fun getMpesaNumbersOnDevice(
        context: Context,
        onSuccess: (phoneNumbers: List<String>) -> Unit,
        onFailure: (errorMessage: String) -> Unit
    )

    /** set mpesa phone number */
    suspend fun setMpesaNumber(
        mpesaNumber: String,
        onSuccess: () -> Unit,
        onFailure: (errorMessage: String) -> Unit
    )

    /** sets/updates the PIN*/
    suspend fun setPin(
        pin: String,
        onSuccess: () -> Unit,
        onFailure: (errorMessage: String) -> Unit
    )

    /** verifies in the input Pin is the correct one*/
    fun verifyPin(
        pin: String,
        onSuccess: (Boolean) -> Unit,
        onFailure: (errorMessage: String) -> Unit
    )

}