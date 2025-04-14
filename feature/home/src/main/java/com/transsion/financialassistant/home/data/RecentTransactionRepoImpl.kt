package com.transsion.financialassistant.home.data

import com.transsion.financialassistant.data.room.db.FinancialAssistantDao
import com.transsion.financialassistant.data.room.db.UnifiedTransaction
import com.transsion.financialassistant.home.domain.RecentTransactionRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecentTransactionRepoImpl @Inject constructor(
    private val dao: FinancialAssistantDao
): RecentTransactionRepo {
    override suspend fun getTotalMoneyIn(): Result<Double> {
       TODO("Not yet implemented")
    }

    override suspend fun getTotalMoneyOut(): Result<Double> {
        TODO("Not yet implemented")
    }

    override  fun getRecentTransactions(): Flow<List<UnifiedTransaction>> {
        return dao.getRecentTransactions()
    }
}