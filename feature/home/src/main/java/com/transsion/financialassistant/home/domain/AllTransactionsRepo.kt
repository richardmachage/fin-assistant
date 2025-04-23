package com.transsion.financialassistant.home.domain

import androidx.paging.PagingData
import com.transsion.financialassistant.data.room.entities.receive_pochi.ReceivePochiEntity
import com.transsion.financialassistant.data.room.views.personal.UnifiedTransactionPersonal
import com.transsion.financialassistant.home.screens.all_transactions.filter.FilterState
import kotlinx.coroutines.flow.Flow

interface AllTransactionsRepo {

    suspend fun getTotalMoneyIn(): Result<Double>

    suspend fun getTotalMoneyOut(): Result<Double>

    suspend fun getNumOfTransactionsIn(): Result<Int>

    suspend fun getNumOfTransactionsOut(): Result<Int>

    fun getAllTransactions(filterState: FilterState): Flow<PagingData<UnifiedTransactionPersonal>>

    fun getAllBusinessTransactions(): Flow<PagingData<ReceivePochiEntity>>
}