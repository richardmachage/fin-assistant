package com.transsion.financialassistant.data.repository.transaction.send_pochi

import android.content.Context

interface SendPochiRepo {
    suspend fun insertSendPochiTransaction(
        message: String,
        context: Context,
        subId: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
        phone: String? = null
    )
}