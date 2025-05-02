package com.transsion.financialassistant.feedback.domain

import com.transsion.financialassistant.feedback.navigation.FeedbackRoutes

interface FeedBackRepo {
    suspend fun sendFeedback(
        feedback: FeedbackRoutes.Feedback,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
}