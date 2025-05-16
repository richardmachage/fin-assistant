package com.transsion.financialassistant.home.domain

import com.transsion.financialassistant.data.models.InsightCategory
import com.transsion.financialassistant.data.room.views.personal.UnifiedTransactionPersonal
import kotlinx.coroutines.flow.Flow

interface RecentTransactionRepo {


    suspend fun getTotalMoneyIn(insightCategory: InsightCategory): Flow<Double>

    suspend fun getTotalMoneyOut(insightCategory: InsightCategory): Flow<Double>

    fun getRecentTransactions(insightCategory: InsightCategory): Flow<List<UnifiedTransactionPersonal>>

    fun getMpesaBalance(insightCategory: InsightCategory): Flow<Double>

    fun getNumOfAllTransactions(insightCategory: InsightCategory): Flow<Int>
}