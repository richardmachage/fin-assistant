package com.transsion.financialassistant.insights.data

import com.transsion.financialassistant.data.cache.AppCache
import com.transsion.financialassistant.data.room.db.FinancialAssistantDao
import com.transsion.financialassistant.insights.domain.InsightsRepo
import javax.inject.Inject

class InsightRepoImpl @Inject constructor(
    private val dao: FinancialAssistantDao
) : InsightsRepo {
    override suspend fun getTotalMoneyIn(startDate: String, endDate: String): Result<Double> {
        val cacheKey = "total_money_in$startDate$endDate"
        return try {
            // First check if exists in cache
            val cache = AppCache.get<Double>(cacheKey)
            cache?.let { fromCache ->
                Result.success(fromCache)
            } ?: run {
                //not in cache, fetch from DB and insert in cache
                val totalMoneyIn = dao.getTotalMoneyInAmount(startDate, endDate) ?: 0.0
                AppCache.put(key = cacheKey, value = totalMoneyIn)
                Result.success(totalMoneyIn)
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMoneyOut(startDate: String, endDate: String): Result<Double> {
        val cacheKey = "total_money_out$startDate$endDate"
        return try {
            // First check if exists in cache
            val cache = AppCache.get<Double>(cacheKey)
            cache?.let { fromCache ->
                Result.success(fromCache)
            } ?: run {
                //not in cache, fetch from DB and insert in cache
                val totalMoneyOut = 0.1//FIXME dao.getTotalMoneyOutAmount(startDate, endDate)?: 0.0
                AppCache.put(key = cacheKey, value = totalMoneyOut)
                Result.success(totalMoneyOut)
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTransactionsNumOfIn(startDate: String, endDate: String): Result<Int> {
        val cacheKey = "number_of_transactions_in$startDate$endDate"

        return try {
            // First check if exists in cache
            val cache = AppCache.get<Int>(cacheKey)
            cache?.let { fromCache ->
                Result.success(fromCache)
            } ?: run {
                //not in cache, fetch from DB and insert in cache
                val numOfTransactions = dao.getNumberOfTransactionsIn(startDate, endDate) ?: 0

                AppCache.put(key = cacheKey, value = numOfTransactions)
                Result.success(numOfTransactions)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getTransactionsOut(startDate: String, endDate: String) {
        TODO("Not yet implemented")
    }

    override fun getTotalTransactions(startDate: String, endDate: String) {
        TODO("Not yet implemented")
    }

}