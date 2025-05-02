package com.transsion.financialassistant.insights.domain

import com.transsion.financialassistant.data.models.InsightCategory
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType
import com.transsion.financialassistant.insights.model.InsightTimeline
import com.transsion.financialassistant.insights.model.TransactionUi
import com.transsion.financialassistant.presentation.components.graphs.model.CategoryDistribution
import com.transsion.financialassistant.presentation.components.graphs.model.DataPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface InsightsRepo {
    val categoryDistributionFlow: StateFlow<List<CategoryDistribution>>


    suspend fun getTotalMoneyIn(
        startDate: String,
        endDate: String,
        insightCategory: InsightCategory
    ): Flow<Double>

    suspend fun getTotalMoneyOut(
        startDate: String,
        endDate: String,
        insightCategory: InsightCategory
    ): Flow<Double>

    suspend fun getNumOfTransactionsIn(
        startDate: String,
        endDate: String,
        insightCategory: InsightCategory
    ): Flow<Int>

    suspend fun getNumOfTransactionsOut(
        startDate: String,
        endDate: String,
        insightCategory: InsightCategory
    ): Flow<Int>


    fun getTotalTransactions(startDate: String, endDate: String)

    fun getDataPoints(
        insightCategory: InsightCategory,
        transactionCategory: TransactionCategory,
        insightTimeline: InsightTimeline
    ): Flow<List<DataPoint>>

    suspend fun getTotalTransactionCost(startDate: String, endDate: String): Flow<Double>

    fun getDataPointsForCategory(
        startDate: String,
        endDate: String,
        transactionType: TransactionType
    ): Flow<List<DataPoint>>

    fun getDataForCategory(
        startDate: String,
        endDate: String,
        transactionType: TransactionType,
        transactionCategory: TransactionCategory
    ): Flow<List<TransactionUi>>
}