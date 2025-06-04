package com.transsion.financialassistant.home.data

import com.transsion.financialassistant.data.models.InsightCategory
import com.transsion.financialassistant.data.room.db.FinancialAssistantDao
import com.transsion.financialassistant.data.room.views.business.UnifiedTransactionsBusinessDao
import com.transsion.financialassistant.data.room.views.personal.UnifiedTransactionPersonal
import com.transsion.financialassistant.data.utils.dbFormatter
import com.transsion.financialassistant.home.domain.RecentTransactionRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapNotNull
import java.time.LocalDate
import javax.inject.Inject

class RecentTransactionRepoImpl @Inject constructor(
    private val dao: FinancialAssistantDao,
    private val businessDao: UnifiedTransactionsBusinessDao,
) : RecentTransactionRepo {
    private val todayDate = LocalDate.now().format(dbFormatter)

    override suspend fun getTotalMoneyIn(insightCategory: InsightCategory): Flow<Double> {

        return when (insightCategory) {
            InsightCategory.PERSONAL -> dao.getTotalMoneyInAmount(
                startDate = todayDate,
                endDate = todayDate
            )

            InsightCategory.BUSINESS -> businessDao.getTotalMoneyInAmount(
                startDate = todayDate,
                endDate = todayDate
            )
        }


    }

    override suspend fun getTotalMoneyOut(
        insightCategory: InsightCategory
    ): Flow<Double> {
        return when (insightCategory) {
            InsightCategory.PERSONAL -> dao.getTotalMoneyOutAmount(
                startDate = todayDate,
                endDate = todayDate
            )

            InsightCategory.BUSINESS -> businessDao.getTotalMoneyOutAmount(
                startDate = todayDate,
                endDate = todayDate
            )
        }
    }

    override fun getRecentTransactions(insightCategory: InsightCategory): Flow<List<UnifiedTransactionPersonal>> {
        return when (insightCategory) {
            InsightCategory.PERSONAL -> dao.getRecentTransactions()
            InsightCategory.BUSINESS -> {
                businessDao.getRecentTransactions()
                    .mapNotNull { list ->
                        list.mapNotNull {
                            UnifiedTransactionPersonal(
                                transactionCode = it.transactionCode,
                                phone = it.phone,
                                amount = it.amount,
                                date = it.date,
                                time = it.time,
                                name = it.name,
                                transactionCost = it.transactionCost,
                                mpesaBalance = it.mpesaBalance,
                                transactionCategory = it.transactionCategory,
                                transactionType = it.transactionType
                            )
                        }
                    }
            }

        }
    }

    override fun getMpesaBalance(insightCategory: InsightCategory) = flow<Double> {
        when (insightCategory) {
            InsightCategory.PERSONAL -> {
                emit(dao.getMpesaBalance().first())
            }

            InsightCategory.BUSINESS -> {
                emit(getPochiBalance().first() + getTillBalance().first())
            }
        }
    }

    override fun getPochiBalance(): Flow<Double> {
        return businessDao.getBusinessBalance()
    }

    override fun getTillBalance(): Flow<Double> {

        //FIXME change to retrieve till balance
        return flowOf(0.0)//businessDao.getBusinessBalance()
    }

    override fun getNumOfAllTransactions(insightCategory: InsightCategory): Flow<Int> {
        return when (insightCategory) {
            InsightCategory.PERSONAL -> dao.getNumOfAllTransactions()
            InsightCategory.BUSINESS -> businessDao.getNumOfAllTransactions()

        }
    }
}