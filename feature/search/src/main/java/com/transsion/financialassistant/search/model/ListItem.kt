package com.transsion.financialassistant.search.model

import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType

data class ListItem(
    val transactionCode: String,
    val transactionType: TransactionType,
    val transactionCategory: TransactionCategory,
    val title: String
)
