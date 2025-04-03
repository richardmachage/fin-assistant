package com.transsion.financialassistant.insights.domain

interface InsightsRepo {
    fun getTotalMoneyIn(startDate: String, endDate: String)

    fun getMoneyOut(startDate: String, endDate: String)

    fun getTransactionsIn(startDate: String, endDate: String)

    fun getTransactionsOut(startDate: String, endDate: String)

    fun getTotalTransactions(startDate: String, endDate: String)

}