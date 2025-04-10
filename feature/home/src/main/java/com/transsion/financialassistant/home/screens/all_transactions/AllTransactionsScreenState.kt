package com.transsion.financialassistant.home.screens.all_transactions

import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.home.screens.all_transactions.model.AllTransactionsCategory
import com.transsion.financialassistant.home.screens.all_transactions.model.AllTransactionsTimeline

data class AllTransactionsScreenState(
    val isLoading: Boolean = false,
    val toastMessage: String? = null,
    val transactionCategory: TransactionCategory = TransactionCategory.IN,
    val allTransactionsCategory: AllTransactionsCategory = AllTransactionsCategory.PERSONAL,
    val allTransactionsTimeline: AllTransactionsTimeline = AllTransactionsTimeline.TODAY,

    val moneyIn: String? = null,
    val moneyOut: String? = null,
    val transactionsIn: String? = null,
    val transactionsOut: String? = null,
)

