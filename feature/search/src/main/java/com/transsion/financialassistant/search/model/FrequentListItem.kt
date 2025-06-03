package com.transsion.financialassistant.search.model

import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType

data class FrequentListItem(
    val name: String,
    val transactionType: TransactionType,
    val transactionCategory: TransactionCategory,
    val title: String
)
