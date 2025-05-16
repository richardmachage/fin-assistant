package com.transsion.financialassistant.data.repository.transaction.send_money

import android.content.Context
import com.transsion.financialassistant.data.room.entities.send_money.SendMoneyEntity
import kotlinx.coroutines.flow.Flow

interface SendMoneyRepo {

    suspend fun insertSendMoneyTransaction(
        message: String,
        context: Context,
        subId: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
        phone: String? = null
        )

    suspend fun getAllSendMoneyTransactions(): Flow<List<SendMoneyEntity>>
}