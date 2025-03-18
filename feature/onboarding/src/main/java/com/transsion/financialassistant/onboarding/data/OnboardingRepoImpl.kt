package com.transsion.financialassistant.onboarding.data

import com.transsion.financialassistant.data.preferences.DatastorePreferences
import com.transsion.financialassistant.data.preferences.SharedPreferences
import com.transsion.financialassistant.onboarding.domain.OnboardingRepo
import javax.inject.Inject

class OnboardingRepoImpl @Inject constructor(
    private val datastorePreferences: DatastorePreferences,
    private val sharedPreferences: SharedPreferences
) : OnboardingRepo {


    override fun hasCompletedOnboarding(): Boolean {
        //check the onboarding flag ,
        //if empty then the it is first installation, otherwise it is not

        val status = sharedPreferences.loadData(SharedPreferences.ONBOARDING_COMPLETED_KEY)

        return status?.let { true } ?: false

    }

    override fun setCompletedOnboarding() {
        sharedPreferences.saveData(
            key = SharedPreferences.ONBOARDING_COMPLETED_KEY,
            value = SharedPreferences.ONBOARDING_COMPLETED_KEY
        )
    }
}