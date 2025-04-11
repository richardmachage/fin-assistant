package com.transsion.financialassistant.home.screens.all_transactions.domain

interface AllTransactionsRepo {

    suspend fun getTotalMoneyIn(): Result<Double>

    suspend fun getTotalMoneyOut(): Result<Double>

    suspend fun getNumOfTransactionsIn(): Result<Int>

    suspend fun getNumOfTransactionsOut(): Result<Int>
}