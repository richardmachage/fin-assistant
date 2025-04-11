package com.transsion.financialassistant.onboarding.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.transsion.financialassistant.home.screens.HomeScreen
import com.transsion.financialassistant.onboarding.screens.change_number.ChangePhoneNumberInstructions
import com.transsion.financialassistant.onboarding.screens.confirm_number.ConfirmNumberDualScreen
import com.transsion.financialassistant.onboarding.screens.create_pin.CreatePinScreen
import com.transsion.financialassistant.onboarding.screens.create_pin.SetPasswordPromptScreen
import com.transsion.financialassistant.onboarding.screens.get_started.GetStarted
import com.transsion.financialassistant.onboarding.screens.login.LoginScreen
import com.transsion.financialassistant.onboarding.screens.promt_screens.enable_notifications.EnableNotificationScreen
import com.transsion.financialassistant.onboarding.screens.surveys.PersonalTrackerSurvey
import com.transsion.financialassistant.onboarding.screens.surveys.SurveyBusinessScreens
import com.transsion.financialassistant.onboarding.screens.surveys.SurveyScreen

@RequiresApi(Build.VERSION_CODES.Q)
fun NavGraphBuilder.onboardingNavGraph(
    navController: NavController,
    goToLanding: (route: Any) -> Unit
) {
    composable<OnboardingRoutes.Welcome> {
        GetStarted(
            navController = navController,
        )
    }


    composable<OnboardingRoutes.CreatePin> {
        CreatePinScreen(navController = navController)
    }

    composable<OnboardingRoutes.Login> {
        LoginScreen(
            navController = navController,
            goToLanding = goToLanding
        )
    }

    composable<OnboardingRoutes.ChangeNumber> {
        ChangePhoneNumberInstructions(navController = navController)
    }

    composable<OnboardingRoutes.ConfirmNumberDual> {
        ConfirmNumberDualScreen(navController = navController)
    }

    composable<OnboardingRoutes.SetPassword> {
        SetPasswordPromptScreen(onSkip = {}, onContinue = {})
    }

    composable<OnboardingRoutes.EnableNotifications> {
        EnableNotificationScreen(navController = navController)
    }

    composable<OnboardingRoutes.SurveyScreen> {
        SurveyScreen(navController = navController)
    }

    composable<OnboardingRoutes.PersonalTrackerSurvey> {
        PersonalTrackerSurvey(
            navController = navController,
            goToLanding = goToLanding
        )
    }

    composable<OnboardingRoutes.SurveyBusinessScreens> {
        SurveyBusinessScreens(
            navController = navController,
            goToLanding = goToLanding
        )
    }

    composable<OnboardingRoutes.HomeScreen> {
        HomeScreen()
    }


}