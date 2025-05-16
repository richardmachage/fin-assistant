package com.transsion.financialassistant.feedback.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface FeedbackRoutes {

    @Serializable
    data object Feedback : FeedbackRoutes
}