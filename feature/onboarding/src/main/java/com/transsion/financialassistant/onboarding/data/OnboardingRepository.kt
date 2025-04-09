package com.transsion.financialassistant.onboarding.data

import kotlinx.coroutines.flow.Flow

interface OnboardingRepository {
    val onboardingCompleted: Flow<Boolean>

    suspend fun savePurpose(purpose: String)

    suspend fun savePersonalExpenses(expenses: String)

    suspend fun saveBusinessDetails(type: String, expenses: String, paymentMethod: String)

    suspend fun setOnboardingCompleted(completed: Boolean)
}
