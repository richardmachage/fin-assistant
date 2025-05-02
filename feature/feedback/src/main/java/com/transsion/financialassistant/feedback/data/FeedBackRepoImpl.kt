package com.transsion.financialassistant.feedback.data

import com.transsion.financialassistant.feedback.domain.FeedBackRepo
import com.transsion.financialassistant.feedback.model.FeedBack
import kotlinx.coroutines.delay
import javax.inject.Inject

class FeedBackRepoImpl @Inject constructor() : FeedBackRepo {
    override suspend fun sendFeedback(
        feedback: FeedBack,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        delay(2200)

        if (feedback.title.lowercase() == "error") {
            onError("Failed to send feedback")
        } else {
            onSuccess()
        }
    }

}