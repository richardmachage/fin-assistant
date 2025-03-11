package com.transsion.financialassistant.onboarding.navigation

import kotlinx.serialization.Serializable


@Serializable
sealed interface OnboardingRoutes {
    @Serializable
    object Welcome : OnboardingRoutes

    @Serializable
    object ConfirmNumber : OnboardingRoutes

    @Serializable
    object CreatePin : OnboardingRoutes

}