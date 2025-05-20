package com.transsion.financialassistant.data.room.entities.reversal_credit

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.transsion.financialassistant.data.models.TransactionCategory

@Entity
data class ReversalCreditEntity(
    @PrimaryKey
    val transactionCode: String,
    val transactionReversedCode: String,
    val phone: String,
    val amount: Double,
    val mpesaBalance: Double,
    val date: String,
    val time: String,
    //val transactionType: TransactionType.
    val transactionCategory: TransactionCategory = TransactionCategory.IN
)
