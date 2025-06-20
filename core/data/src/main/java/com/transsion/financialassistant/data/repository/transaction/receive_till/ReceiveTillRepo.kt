package com.transsion.financialassistant.data.repository.transaction.receive_till

import android.content.Context

interface ReceiveTillRepo {
    suspend fun receiveTillTransaction(
        message: String,
        context: Context,
        subId: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
        phone: String? = null,
    )
}