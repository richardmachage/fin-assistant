package com.transsion.financialassistant.data.repository.transaction.send_till

import android.content.Context

interface SendTillRepo {
    suspend fun insertSendTillTransaction(
        message: String,
        context: Context,
        subId: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
        phone: String? = null
    )
}