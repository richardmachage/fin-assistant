package com.transsion.financialassistant.home.data

import com.transsion.financialassistant.data.room.db.FinancialAssistantDao
import com.transsion.financialassistant.data.room.db.UnifiedTransaction
import com.transsion.financialassistant.data.utils.dbFormatter
import com.transsion.financialassistant.home.domain.RecentTransactionRepo
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class RecentTransactionRepoImpl @Inject constructor(
    private val dao: FinancialAssistantDao
): RecentTransactionRepo {
    private val todayDate = LocalDate.now().format(dbFormatter)

    override suspend fun getTotalMoneyIn(): Result<Double> {
        return try {
            Result.success(
                dao.getTotalMoneyInAmount(startDate = todayDate, endDate = todayDate) ?: 0.0
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTotalMoneyOut(): Result<Double> {
        return try {
            Result.success(
                dao.getTotalMoneyOutAmount(startDate = todayDate, endDate = todayDate) ?: 0.0
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override  fun getRecentTransactions(): Flow<List<UnifiedTransaction>> {
        return dao.getRecentTransactions()
    }

    override fun getMpesaBalance(): Flow<Double> {
        return dao.getMpesaBalance()
    }
}