package com.transsion.financialassistant.data.repository.transaction.receive_money

import android.content.Context

interface ReceiveMoneyRepo {
    suspend fun insertReceiveMoneyTransaction(
        message: String,
        context: Context,
        subId: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
        phone: String? = null
    )
}