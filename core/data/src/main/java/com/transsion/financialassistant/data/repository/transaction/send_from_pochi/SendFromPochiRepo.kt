package com.transsion.financialassistant.data.repository.transaction.send_from_pochi

import android.content.Context

interface SendFromPochiRepo {
    suspend fun insertSendFromPochiTransaction(
        message: String,
        context: Context,
        subId: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
        phone: String? = null
    )
}