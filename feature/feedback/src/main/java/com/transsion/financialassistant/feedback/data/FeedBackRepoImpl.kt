package com.transsion.financialassistant.feedback.data

import com.google.firebase.firestore.FirebaseFirestore
import com.transsion.financialassistant.feedback.FEEDBACK_COLLECTION
import com.transsion.financialassistant.feedback.domain.FeedBackRepo
import com.transsion.financialassistant.feedback.model.FeedBack
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FeedBackRepoImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : FeedBackRepo {
    override suspend fun sendFeedback(
        feedback: FeedBack,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        firestore.collection(FEEDBACK_COLLECTION)
            .add(feedback)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message.toString())
            }
            .await()
    }

}