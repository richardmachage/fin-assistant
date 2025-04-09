package com.transsion.financialassistant.onboarding.data

import com.transsion.financialassistant.data.preferences.DatastorePreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
    private val datastorePreferences: DatastorePreferences
) : OnboardingRepository {

    override val onboardingCompleted: Flow<Boolean> = datastorePreferences.getOnboardingCompleted(false)

    override suspend fun savePurpose(purpose: String) {
        datastorePreferences.savePurpose(purpose)
    }

    override suspend fun savePersonalExpenses(expenses: String) {
        datastorePreferences.savePersonalExpenses(expenses)
    }

    override suspend fun saveBusinessDetails(type: String, expenses: String, paymentMethod: String) {
        datastorePreferences.saveBusinessDetails(type, expenses, paymentMethod)
    }

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        datastorePreferences.saveOnboardingCompleted(completed)
    }
}
