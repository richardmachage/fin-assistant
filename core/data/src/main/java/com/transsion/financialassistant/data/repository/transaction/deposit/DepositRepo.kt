package com.transsion.financialassistant.data.repository.transaction.deposit

import android.content.Context

interface DepositRepo {
    suspend fun insertDepositTransaction(
        message: String,
        context: Context,
        subId: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
        phone: String? = null
    )
}