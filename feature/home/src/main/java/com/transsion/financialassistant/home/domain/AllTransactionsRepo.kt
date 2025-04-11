package com.transsion.financialassistant.home.domain

import androidx.paging.PagingData
import com.transsion.financialassistant.data.room.db.UnifiedTransaction
import kotlinx.coroutines.flow.Flow

interface AllTransactionsRepo {

    suspend fun getTotalMoneyIn(): Result<Double>

    suspend fun getTotalMoneyOut(): Result<Double>

    suspend fun getNumOfTransactionsIn(): Result<Int>

    suspend fun getNumOfTransactionsOut(): Result<Int>

    fun getAllTransactions(): Flow<PagingData<UnifiedTransaction>>
}