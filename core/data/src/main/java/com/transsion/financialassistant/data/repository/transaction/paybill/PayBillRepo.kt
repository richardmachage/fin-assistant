package com.transsion.financialassistant.data.repository.transaction.paybill

import android.content.Context

interface PayBillRepo {
    suspend fun insertPayBillTransaction(
        message: String,
        context: Context,
        subId: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
        phone: String? = null
    )
}