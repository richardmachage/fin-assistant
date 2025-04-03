package com.transsion.financialassistant.data.room.models

import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType

data class UnifiedIncomingTransaction(
    val transactionCode: String,
    val phone: String,
    val amount: Double,
    val date: String,
    val time: String,
    val transactionCategory: TransactionCategory,
    val transactionType: TransactionType
)
