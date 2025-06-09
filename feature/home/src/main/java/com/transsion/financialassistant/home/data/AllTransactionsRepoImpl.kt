package com.transsion.financialassistant.home.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.transsion.financialassistant.data.cache.AppCache
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.room.db.FinancialAssistantDao
import com.transsion.financialassistant.data.room.entities.receive_pochi.ReceivePochiDao
import com.transsion.financialassistant.data.room.views.business.UnifiedTransactionBusiness
import com.transsion.financialassistant.data.room.views.business.UnifiedTransactionsBusinessDao
import com.transsion.financialassistant.data.room.views.personal.UnifiedTransactionPersonal
import com.transsion.financialassistant.data.room.views.personal.UnifiedTransactionsPersonalDao
import com.transsion.financialassistant.data.utils.dbFormatter
import com.transsion.financialassistant.data.utils.getLastMonthRange
import com.transsion.financialassistant.data.utils.getLastWeekRange
import com.transsion.financialassistant.data.utils.getMonthRange
import com.transsion.financialassistant.data.utils.getWeekRange
import com.transsion.financialassistant.home.domain.AllTransactionsRepo
import com.transsion.financialassistant.home.screens.all_transactions.filter.FilterPeriod
import com.transsion.financialassistant.home.screens.all_transactions.filter.FilterState
import kotlinx.coroutines.flow.Flow
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject


class AllTransactionsRepoImpl @Inject constructor(
    val dao: FinancialAssistantDao,
    private val unifiedTransactionsDao: UnifiedTransactionsPersonalDao,
    private val receivePochiDao: ReceivePochiDao,
    private val businessDao: UnifiedTransactionsBusinessDao
) : AllTransactionsRepo {

    private val TAG = "AllTransactionRepoImpl"

    override suspend fun getTotalMoneyIn(): Result<Double> {
        val cacheKey = "total_money_in"
        return try {
            // First check if exists in cache
            val cache = AppCache.get<Double>(cacheKey)
            cache?.let { fromCache ->
                Result.success(fromCache)
            } ?: run {
                //not in cache, fetch from DB and insert in cache
                val totalMoneyIn = dao.getAllTransactionMoneyInAmount() ?: 0.0
                AppCache.put(key = cacheKey, value = totalMoneyIn)
                Result.success(totalMoneyIn)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTotalMoneyOut(): Result<Double> {
        val cacheKey = "total_money_out"

        return try {
            // First check if exists in cache
            val cache = AppCache.get<Double>(cacheKey)
            cache?.let { fromCache ->
                Result.success(fromCache)
            } ?: run {
                //not in cache, fetch from DB and insert in cache
                val totalMoneyOut = dao.getAllTransactionMoneyOutAmount() ?: 0.0
                AppCache.put(key = cacheKey, value = totalMoneyOut)
                Result.success(totalMoneyOut)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getNumOfTransactionsIn(): Result<Int> {
        val cacheKey = "number_of_all_transactions_in"

        return try {
            // First check if exists in cache
            val cache = AppCache.get<Int>(cacheKey)
            cache?.let { fromCache ->
                Result.success(fromCache)
            } ?: run {
                //not in cache, fetch from DB and insert in cache
                val numOfTransactions = dao.getNumberofAllTransactionsIn() ?: 0

                AppCache.put(key = cacheKey, value = numOfTransactions)
                Result.success(numOfTransactions)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getNumOfTransactionsOut(): Result<Int> {
        val cacheKey = "number_of_all_transactions_out"

        return try {
            // First check if exists in cache
            val cache = AppCache.get<Int>(cacheKey)
            cache?.let { fromCache ->
                Result.success(fromCache)
            } ?: run {
                //not in cache, fetch from DB and insert in cache
                val numOfTransactions = dao.getNumberofAllTransactionsOut() ?: 0

                AppCache.put(key = cacheKey, value = numOfTransactions)
                Result.success(numOfTransactions)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getAllTransactions(filterState: FilterState): Flow<PagingData<UnifiedTransactionPersonal>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
            ),
            pagingSourceFactory = {
                //byPeriodAndSource(filterState)
                byPeriodAndSourcePersonal(filterState)
            }
        ).flow
    }

    override fun getAllBusinessTransactions(filterState: FilterState): Flow<PagingData<UnifiedTransactionBusiness>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = {
                //receivePochiDao.getAllPaged()
                byPeriodAndSourceBusiness(filterState)
            }
        ).flow
    }


    private fun byPeriodBusinessFilter(period: FilterPeriod?) = when (period) {
        FilterPeriod.MOST_RECENT -> {
            businessDao.getAllTransactions()
        }

        FilterPeriod.OLDEST_FIRST -> {
            businessDao.getAllTransactionsReverse()
        }

        FilterPeriod.TODAY -> {
            val today = LocalDate.now().format(dbFormatter)
            businessDao.getAllTransactionsForDate(today, today)
        }

        FilterPeriod.YESTERDAY -> {
            val yesterday =
                LocalDate.now().minusDays(1).format(dbFormatter)
            businessDao.getAllTransactionsForDate(yesterday, yesterday)
        }

        FilterPeriod.THIS_WEEK -> {
            val (startWeek, endWeek) = LocalDate.now().getWeekRange(DayOfWeek.SUNDAY)
            businessDao.getAllTransactionsForDate(startWeek, endWeek)
        }

        FilterPeriod.LAST_WEEK -> {
            val (startWeek, endWeek) = LocalDate.now().getLastWeekRange(DayOfWeek.SUNDAY)
            businessDao.getAllTransactionsForDate(startWeek, endWeek)
        }

        FilterPeriod.THIS_MONTH -> {
            val (startMonth, endMonth) = LocalDate.now().getMonthRange()
            businessDao.getAllTransactionsForDate(startMonth, endMonth)
        }

        FilterPeriod.LAST_MONTH -> {
            val (startMonth, endMonth) = LocalDate.now().getLastMonthRange()
            businessDao.getAllTransactionsForDate(startMonth, endMonth)
        }

        null -> businessDao.getAllTransactions()

    }

    private fun byPeriodPersonalFilter(period: FilterPeriod?) = when (period) {
        FilterPeriod.MOST_RECENT -> {
            unifiedTransactionsDao.getAllTransactions()
        }

        FilterPeriod.OLDEST_FIRST -> {
            unifiedTransactionsDao.getAllTransactionsReverse()
        }

        FilterPeriod.TODAY -> {
            val today = LocalDate.now().format(dbFormatter)
            unifiedTransactionsDao.getAllTransactionsForDate(today, today)
        }

        FilterPeriod.YESTERDAY -> {
            val yesterday =
                LocalDate.now().minusDays(1).format(dbFormatter)
            unifiedTransactionsDao.getAllTransactionsForDate(yesterday, yesterday)
        }

        FilterPeriod.THIS_WEEK -> {
            val (startWeek, endWeek) = LocalDate.now().getWeekRange(DayOfWeek.SUNDAY)
            unifiedTransactionsDao.getAllTransactionsForDate(startWeek, endWeek)
        }

        FilterPeriod.LAST_WEEK -> {
            val (startWeek, endWeek) = LocalDate.now().getLastWeekRange(DayOfWeek.SUNDAY)
            unifiedTransactionsDao.getAllTransactionsForDate(startWeek, endWeek)
        }

        FilterPeriod.THIS_MONTH -> {
            val (startMonth, endMonth) = LocalDate.now().getMonthRange()
            unifiedTransactionsDao.getAllTransactionsForDate(startMonth, endMonth)
        }

        FilterPeriod.LAST_MONTH -> {
            val (startMonth, endMonth) = LocalDate.now().getLastMonthRange()
            unifiedTransactionsDao.getAllTransactionsForDate(startMonth, endMonth)
        }

        null -> {
            unifiedTransactionsDao.getAllTransactions()
        }
    }


    private fun byPeriodAndSourcePersonal(filterState: FilterState) = when (filterState.period) {
        FilterPeriod.MOST_RECENT -> {
            Log.d(TAG, "Fetching MOST_RECENT transactions with source: ${filterState.source}")
            when (filterState.source) {
                TransactionCategory.IN -> unifiedTransactionsDao.getAllTransactionsIn()
                TransactionCategory.OUT -> unifiedTransactionsDao.getAllTransactionsOut()
                null -> byPeriodPersonalFilter(filterState.period)
            }
        }

        FilterPeriod.OLDEST_FIRST -> {
            Log.d(TAG, "Fetching OLDEST_FIRST transactions with source: ${filterState.source}")
            when (filterState.source) {
                TransactionCategory.IN -> unifiedTransactionsDao.getAllTransactionsInReverse()
                TransactionCategory.OUT -> unifiedTransactionsDao.getAllTransactionsOutReverse()
                null -> byPeriodPersonalFilter(filterState.period)
            }
        }

        FilterPeriod.TODAY -> {
            val today = LocalDate.now().format(dbFormatter)
            Log.d(TAG, "Fetching TODAY's transactions ($today) with source: ${filterState.source}")
            when (filterState.source) {
                TransactionCategory.IN -> unifiedTransactionsDao.getAllTransactionsInForDate(
                    today,
                    today
                )

                TransactionCategory.OUT -> unifiedTransactionsDao.getAllTransactionsOutForDate(
                    today,
                    today
                )

                null -> byPeriodPersonalFilter(filterState.period)
            }
        }

        FilterPeriod.YESTERDAY -> {
            val yesterday = LocalDate.now().minusDays(1).format(dbFormatter)
            Log.d(
                TAG,
                "Fetching YESTERDAY's transactions ($yesterday) with source: ${filterState.source}"
            )
            when (filterState.source) {
                TransactionCategory.IN -> unifiedTransactionsDao.getAllTransactionsInForDate(
                    yesterday,
                    yesterday
                )

                TransactionCategory.OUT -> unifiedTransactionsDao.getAllTransactionsForDate(
                    yesterday,
                    yesterday
                )

                null -> byPeriodPersonalFilter(filterState.period)
            }
        }

        FilterPeriod.THIS_WEEK -> {
            val (startWeek, endWeek) = LocalDate.now().getWeekRange(DayOfWeek.SUNDAY)
            Log.d(
                TAG,
                "Fetching THIS_WEEK's transactions ($startWeek to $endWeek) with source: ${filterState.source}"
            )
            when (filterState.source) {
                TransactionCategory.IN -> unifiedTransactionsDao.getAllTransactionsInForDate(
                    startWeek,
                    endWeek
                )

                TransactionCategory.OUT -> unifiedTransactionsDao.getAllTransactionsOutForDate(
                    startWeek,
                    endWeek
                )

                null -> byPeriodPersonalFilter(filterState.period)
            }
        }

        FilterPeriod.LAST_WEEK -> {
            val (startWeek, endWeek) = LocalDate.now().getLastWeekRange(DayOfWeek.SUNDAY)
            Log.d(
                TAG,
                "Fetching LAST_WEEK's transactions ($startWeek to $endWeek) with source: ${filterState.source}"
            )
            when (filterState.source) {
                TransactionCategory.IN -> unifiedTransactionsDao.getAllTransactionsInForDate(
                    startWeek,
                    endWeek
                )

                TransactionCategory.OUT -> unifiedTransactionsDao.getAllTransactionsOutForDate(
                    startWeek,
                    endWeek
                )

                null -> byPeriodPersonalFilter(filterState.period)
            }
        }

        FilterPeriod.THIS_MONTH -> {
            val (startMonth, endMonth) = LocalDate.now().getMonthRange()
            Log.d(
                TAG,
                "Fetching THIS_MONTH's transactions ($startMonth to $endMonth) with source: ${filterState.source}"
            )
            when (filterState.source) {
                TransactionCategory.IN -> unifiedTransactionsDao.getAllTransactionsInForDate(
                    startMonth,
                    endMonth
                )

                TransactionCategory.OUT -> unifiedTransactionsDao.getAllTransactionsOutForDate(
                    startMonth,
                    endMonth
                )

                null -> byPeriodPersonalFilter(filterState.period)
            }
        }

        FilterPeriod.LAST_MONTH -> {
            val (startMonth, endMonth) = LocalDate.now().getLastMonthRange()
            Log.d(
                TAG,
                "Fetching LAST_MONTH's transactions ($startMonth to $endMonth) with source: ${filterState.source}"
            )
            when (filterState.source) {
                TransactionCategory.IN -> unifiedTransactionsDao.getAllTransactionsInForDate(
                    startMonth,
                    endMonth
                )

                TransactionCategory.OUT -> unifiedTransactionsDao.getAllTransactionsOutForDate(
                    startMonth,
                    endMonth
                )

                null -> byPeriodPersonalFilter(filterState.period)
            }
        }

        null -> {
            Log.d(TAG, "Fetching ALL transactions with source: ${filterState.source}")
            when (filterState.source) {
                TransactionCategory.IN -> unifiedTransactionsDao.getAllTransactionsIn()
                TransactionCategory.OUT -> unifiedTransactionsDao.getAllTransactionsOut()
                null -> byPeriodPersonalFilter(null)
            }
        }
    }

    private fun byPeriodAndSourceBusiness(filterState: FilterState) = when (filterState.period) {
        FilterPeriod.MOST_RECENT -> {
            Log.d(TAG, "Fetching MOST_RECENT transactions with source: ${filterState.source}")
            when (filterState.source) {
                TransactionCategory.IN -> businessDao.getAllTransactionsIn()
                TransactionCategory.OUT -> businessDao.getAllTransactionsOut()
                null -> byPeriodBusinessFilter(filterState.period)
            }
        }

        FilterPeriod.OLDEST_FIRST -> {
            Log.d(TAG, "Fetching OLDEST_FIRST transactions with source: ${filterState.source}")
            when (filterState.source) {
                TransactionCategory.IN -> businessDao.getAllTransactionsInReverse()
                TransactionCategory.OUT -> businessDao.getAllTransactionsOutReverse()
                null -> byPeriodBusinessFilter(filterState.period)
            }
        }

        FilterPeriod.TODAY -> {
            val today = LocalDate.now().format(dbFormatter)
            Log.d(TAG, "Fetching TODAY's transactions ($today) with source: ${filterState.source}")
            when (filterState.source) {
                TransactionCategory.IN -> businessDao.getAllTransactionsInForDate(
                    today,
                    today
                )

                TransactionCategory.OUT -> businessDao.getAllTransactionsOutForDate(
                    today,
                    today
                )

                null -> byPeriodBusinessFilter(filterState.period)
            }
        }

        FilterPeriod.YESTERDAY -> {
            val yesterday = LocalDate.now().minusDays(1).format(dbFormatter)
            when (filterState.source) {
                TransactionCategory.IN -> businessDao.getAllTransactionsInForDate(
                    yesterday,
                    yesterday
                )

                TransactionCategory.OUT -> businessDao.getAllTransactionsForDate(
                    yesterday,
                    yesterday
                )

                null -> byPeriodBusinessFilter(filterState.period)
            }
        }

        FilterPeriod.THIS_WEEK -> {
            val (startWeek, endWeek) = LocalDate.now().getWeekRange(DayOfWeek.SUNDAY)
            when (filterState.source) {
                TransactionCategory.IN -> businessDao.getAllTransactionsInForDate(
                    startWeek,
                    endWeek
                )

                TransactionCategory.OUT -> businessDao.getAllTransactionsOutForDate(
                    startWeek,
                    endWeek
                )

                null -> byPeriodBusinessFilter(filterState.period)
            }
        }

        FilterPeriod.LAST_WEEK -> {
            val (startWeek, endWeek) = LocalDate.now().getLastWeekRange(DayOfWeek.SUNDAY)

            when (filterState.source) {
                TransactionCategory.IN -> businessDao.getAllTransactionsInForDate(
                    startWeek,
                    endWeek
                )

                TransactionCategory.OUT -> businessDao.getAllTransactionsOutForDate(
                    startWeek,
                    endWeek
                )

                null -> byPeriodBusinessFilter(filterState.period)
            }
        }

        FilterPeriod.THIS_MONTH -> {
            val (startMonth, endMonth) = LocalDate.now().getMonthRange()

            when (filterState.source) {
                TransactionCategory.IN -> businessDao.getAllTransactionsInForDate(
                    startMonth,
                    endMonth
                )

                TransactionCategory.OUT -> businessDao.getAllTransactionsOutForDate(
                    startMonth,
                    endMonth
                )

                null -> byPeriodBusinessFilter(filterState.period)
            }
        }

        FilterPeriod.LAST_MONTH -> {
            val (startMonth, endMonth) = LocalDate.now().getLastMonthRange()
            when (filterState.source) {
                TransactionCategory.IN -> businessDao.getAllTransactionsInForDate(
                    startMonth,
                    endMonth
                )

                TransactionCategory.OUT -> businessDao.getAllTransactionsOutForDate(
                    startMonth,
                    endMonth
                )

                null -> byPeriodBusinessFilter(filterState.period)
            }
        }

        null -> {
            Log.d(TAG, "Fetching ALL transactions with source: ${filterState.source}")
            when (filterState.source) {
                TransactionCategory.IN -> businessDao.getAllTransactionsIn()
                TransactionCategory.OUT -> businessDao.getAllTransactionsOut()
                null -> byPeriodBusinessFilter(null)
            }
        }
    }

}

