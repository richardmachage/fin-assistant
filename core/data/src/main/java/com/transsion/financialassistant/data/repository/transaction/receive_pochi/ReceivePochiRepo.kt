package com.transsion.financialassistant.data.repository.transaction.receive_pochi

import android.content.Context

interface ReceivePochiRepo {
    suspend fun insertReceivePochiTransaction(
        message: String,
        context: Context,
        subId: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
        phone: String? = null
    )
}