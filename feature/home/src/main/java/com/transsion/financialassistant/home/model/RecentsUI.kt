package com.transsion.financialassistant.home.model

import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType
import com.transsion.financialassistant.data.room.db.UnifiedTransaction

data class RecentsUI(
    val name: String,
    val amount: Double,
    val date: String,
    val time: String,
    val transactionCategory: TransactionCategory,
    val transactionType: TransactionType
)


fun UnifiedTransaction.toRecentsUI(): RecentsUI {
    return RecentsUI(
        name = this.name ?: "Unknown",
        amount = this.amount,
        date = this.date,
        time = this.time,
        transactionCategory = this.transactionCategory,
        transactionType = this.transactionType
    )
}

