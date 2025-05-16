package com.transsion.financialassistant.feedback.domain

import com.transsion.financialassistant.feedback.model.FeedBack

interface FeedBackRepo {
    suspend fun sendFeedback(
        feedback: FeedBack,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
}