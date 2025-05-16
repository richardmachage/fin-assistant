package com.transsion.financialassistant.feedback.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.transsion.financialassistant.feedback.screens.feedback.FeedBackScreen

fun NavGraphBuilder.feedbackNavGraph(
    navController: NavController
) {

    composable<FeedbackRoutes.Feedback> {
        FeedBackScreen(navController)
    }

}