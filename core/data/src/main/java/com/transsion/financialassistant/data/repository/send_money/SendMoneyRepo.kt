package com.transsion.financialassistant.data.repository.send_money

import android.content.Context

interface SendMoneyRepo {

    suspend fun insertSendMoneyTransaction(
        message: String,
        context: Context,
        subId: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )
}