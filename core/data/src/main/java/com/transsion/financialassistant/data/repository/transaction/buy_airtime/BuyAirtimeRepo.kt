package com.transsion.financialassistant.data.repository.transaction.buy_airtime

import android.content.Context

interface BuyAirtimeRepo {
    suspend fun insertBuyAirtimeTransaction(
        message: String,
        context: Context,
        subId: Int,
        phone: String? = null,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )
}