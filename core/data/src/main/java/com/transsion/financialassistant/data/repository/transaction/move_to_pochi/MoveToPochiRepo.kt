package com.transsion.financialassistant.data.repository.transaction.move_to_pochi

import android.content.Context

interface MoveToPochiRepo {
    suspend fun insertMoveToPochiTransaction(
        message: String,
        context: Context,
        subId: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
        phone: String? = null
    )
}