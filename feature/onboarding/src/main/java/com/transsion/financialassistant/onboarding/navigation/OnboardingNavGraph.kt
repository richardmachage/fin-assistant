package com.transsion.financialassistant.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.transsion.financialassistant.onboarding.screens.change_number.ChangePhoneNumberInstructions
import com.transsion.financialassistant.onboarding.screens.confirm_number.ConfirmNumberScreen
import com.transsion.financialassistant.onboarding.screens.create_pin.CreatePinScreen
import com.transsion.financialassistant.onboarding.screens.get_started.GetStarted
import com.transsion.financialassistant.onboarding.screens.login.LoginScreen

fun NavGraphBuilder.onboardingNavGraph(
    navController: NavController
) {
    composable<OnboardingRoutes.Welcome> {
        GetStarted(
            navController = navController
        )
    }

    composable<OnboardingRoutes.ConfirmNumber> {
        ConfirmNumberScreen(navController = navController)
    }

    composable<OnboardingRoutes.CreatePin> {
        CreatePinScreen(navController = navController)
    }

    composable<OnboardingRoutes.Login> {
        LoginScreen()
    }

    composable<OnboardingRoutes.ChangeNumber> {
        ChangePhoneNumberInstructions(navController = navController)
    }



}