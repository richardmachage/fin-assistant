package com.transsion.financialassistant.home.domain

import com.transsion.financialassistant.data.models.InsightCategory
import com.transsion.financialassistant.data.room.db.UnifiedTransaction
import kotlinx.coroutines.flow.Flow

interface RecentTransactionRepo {


    suspend fun getTotalMoneyIn(): Result<Double>

    suspend fun getTotalMoneyOut(): Result<Double>

    fun getRecentTransactions(insightCategory: InsightCategory): Flow<List<UnifiedTransaction>>

    fun getMpesaBalance(insightCategory: InsightCategory): Flow<Double>

    fun getNumOfAllTransactions(insightCategory: InsightCategory): Flow<Int>
}