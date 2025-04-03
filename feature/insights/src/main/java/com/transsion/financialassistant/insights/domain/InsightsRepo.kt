package com.transsion.financialassistant.insights.domain

interface InsightsRepo {
    suspend fun getTotalMoneyIn(startDate: String, endDate: String): Result<Double>

    suspend fun getMoneyOut(startDate: String, endDate: String): Result<Double>

    suspend fun getTransactionsNumOfIn(startDate: String, endDate: String): Result<Int>

    fun getTransactionsOut(startDate: String, endDate: String)

    fun getTotalTransactions(startDate: String, endDate: String)

}