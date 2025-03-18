package com.transsion.financialassistant.onboarding.domain

interface OnboardingRepo {

    /**Checks if the user has completed onboarding.*/
    fun hasCompletedOnboarding(): Boolean?


    /** Saves the user's onboarding status.*/
    fun setCompletedOnboarding()






}