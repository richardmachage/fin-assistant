package com.transsion.financialassistant.home.screens.all_transactions

import com.transsion.financialassistant.data.models.TransactionCategory

data class AllTransactionsScreenState(
    val isLoading: Boolean = false,
    val toastMessage: String? = null,
    val transactionCategory: TransactionCategory = TransactionCategory.IN,

    val moneyIn: String? = null,
    val moneyOut: String? = null,
    val transactionsIn: String? = null,
    val transactionsOut: String? = null,
)

