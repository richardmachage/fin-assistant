package com.transsion.financialassistant.data.repository.transaction.move_from_pochi

import android.content.Context

interface MoveFromPochiRepo {
    suspend fun insertMoveFromPochiTransaction(
        message: String,
        context: Context,
        subId: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
        phone: String? = null
    )
}