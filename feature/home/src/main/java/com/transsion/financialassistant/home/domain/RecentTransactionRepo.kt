package com.transsion.financialassistant.home.domain

import com.transsion.financialassistant.data.room.db.UnifiedTransaction
import kotlinx.coroutines.flow.Flow

interface RecentTransactionRepo {


    suspend fun getTotalMoneyIn(): Result<Double>

    suspend fun getTotalMoneyOut(): Result<Double>

    fun getRecentTransactions(): Flow<List<UnifiedTransaction>>

    fun getMpesaBalance(): Flow<Double>
}