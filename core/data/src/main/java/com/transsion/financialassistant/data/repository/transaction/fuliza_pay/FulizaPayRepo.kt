package com.transsion.financialassistant.data.repository.transaction.fuliza_pay

import android.content.Context

interface FulizaPayRepo {
    suspend fun insertFulizaPayTransaction(
        message: String,
        context: Context,
        subId: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
        phone: String? = null
    )
}