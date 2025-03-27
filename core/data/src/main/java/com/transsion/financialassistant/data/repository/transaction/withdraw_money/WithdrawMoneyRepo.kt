package com.transsion.financialassistant.data.repository.transaction.withdraw_money

import android.content.Context

interface WithdrawMoneyRepo {
    suspend fun insertWithdrawMoneyTransaction(
        message: String,
        context: Context,
        subId: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
        phone: String? = null
    )
}