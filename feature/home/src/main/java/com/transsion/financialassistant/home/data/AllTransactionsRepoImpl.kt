package com.transsion.financialassistant.home.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.transsion.financialassistant.data.cache.AppCache
import com.transsion.financialassistant.data.room.db.FinancialAssistantDao
import com.transsion.financialassistant.data.room.db.UnifiedTransaction
import com.transsion.financialassistant.home.domain.AllTransactionsRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AllTransactionsRepoImpl @Inject constructor(
    val dao: FinancialAssistantDao
) : AllTransactionsRepo {
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

    override fun getAllTransactions(): Flow<PagingData<UnifiedTransaction>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
            ),
            pagingSourceFactory = { dao.getAllTransactions() }
        ).flow
    }
}