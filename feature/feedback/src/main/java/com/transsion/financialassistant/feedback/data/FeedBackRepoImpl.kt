package com.transsion.financialassistant.feedback.data

import com.transsion.financialassistant.feedback.domain.FeedBackRepo
import com.transsion.financialassistant.feedback.navigation.FeedbackRoutes

class FeedBackRepoImpl() : FeedBackRepo {
    override suspend fun sendFeedback(
        feedback: FeedbackRoutes.Feedback,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        onSuccess()
    }

}