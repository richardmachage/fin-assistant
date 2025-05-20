package com.transsion.financialassistant.data.repository.transaction.reversal_debit

import android.content.Context

interface ReversalDebitRepo {

    suspend fun insertReversalDebitTransaction(
        message: String,
        context: Context,
        subId: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
        phone: String? = null
    )

}