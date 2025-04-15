package com.transsion.financialassistant.home.data

import com.transsion.financialassistant.data.models.InsightCategory
import com.transsion.financialassistant.data.room.db.FinancialAssistantDao
import com.transsion.financialassistant.data.room.db.UnifiedTransaction
import com.transsion.financialassistant.data.room.entities.receive_pochi.ReceivePochiDao
import com.transsion.financialassistant.data.utils.dbFormatter
import com.transsion.financialassistant.home.domain.RecentTransactionRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import java.time.LocalDate
import javax.inject.Inject

class RecentTransactionRepoImpl @Inject constructor(
    private val dao: FinancialAssistantDao,
    private val receivePochiDao: ReceivePochiDao
) : RecentTransactionRepo {
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

    override fun getRecentTransactions(insightCategory: InsightCategory): Flow<List<UnifiedTransaction>> {
        return when (insightCategory) {
            InsightCategory.PERSONAL -> dao.getRecentTransactions()
            InsightCategory.BUSINESS -> receivePochiDao.getRecentTransactions().mapNotNull { list ->
                list.mapNotNull {
                    UnifiedTransaction(
                        transactionCode = it.transactionCode,
                        phone = it.phone,
                        amount = it.amount,
                        date = it.date,
                        time = it.time,
                        name = it.receiveFromName,
                        transactionCost = 0.0,
                        mpesaBalance = it.businessBalance,
                        transactionCategory = it.transactionCategory,
                        transactionType = it.transactionType
                    )
                }
            }
        }
    }

    override fun getMpesaBalance(): Flow<Double> {
        return dao.getMpesaBalance()
    }
}