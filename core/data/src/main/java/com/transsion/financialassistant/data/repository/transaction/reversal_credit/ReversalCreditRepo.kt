package com.transsion.financialassistant.data.repository.transaction.reversal_credit

import android.content.Context

interface ReversalCreditRepo {
    suspend fun insertReversalCreditTransaction(
        message: String,
        context: Context,
        subId: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
        phone: String? = null
    )
}