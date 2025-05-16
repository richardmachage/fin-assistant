package com.transsion.financialassistant.data.repository.transaction.receive_money

import android.content.Context
import com.transsion.financialassistant.data.room.entities.receive_money.ReceiveMoneyEntity
import kotlinx.coroutines.flow.Flow

interface ReceiveMoneyRepo {
    suspend fun insertReceiveMoneyTransaction(
        message: String,
        context: Context,
        subId: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
        phone: String? = null
    )

    // Retrieve Transactions
    suspend fun getAllReceiveMoneyTransactions(): Flow<List<ReceiveMoneyEntity>>
}