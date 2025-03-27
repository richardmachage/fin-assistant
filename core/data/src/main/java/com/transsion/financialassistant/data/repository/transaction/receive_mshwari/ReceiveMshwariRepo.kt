package com.transsion.financialassistant.data.repository.transaction.receive_mshwari

import android.content.Context

interface ReceiveMshwariRepo {
    suspend fun insertReceiveMshwariTransaction(
        message: String,
        context: Context,
        subId: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
        phone: String? = null
    )
}