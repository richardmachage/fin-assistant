package com.transsion.financialassistant.onboarding.di

import com.transsion.financialassistant.onboarding.data.OnboardingRepoImpl
import com.transsion.financialassistant.onboarding.data.OnboardingSurveyRepo
import com.transsion.financialassistant.onboarding.data.OnboardingSurveyRepoImpl
import com.transsion.financialassistant.onboarding.domain.OnboardingRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class OnboardingModule {

    @Binds
    internal abstract fun bindOnboardingRepo(
        onboardingRepoImpl: OnboardingRepoImpl
    ): OnboardingRepo

    @Binds
    internal abstract fun bindRepositoryImpl(
        onboardingRepository: OnboardingSurveyRepoImpl
    ): OnboardingSurveyRepo


}