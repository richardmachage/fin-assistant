package com.transsion.financialassistant.data.room.entities.reversal_debit

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType

@Entity
data class ReversalDebitEntity(
    @PrimaryKey
    val transactionCode: String,
    val phone: String,
    val transactionReversedCode: String,
    val amount: Double,
    val mpesaBalance: Double,
    val date: String,
    val time: String,
    val transactionType: TransactionType = TransactionType.REVERSAL_DEBIT,
    val transactionCategory: TransactionCategory = TransactionCategory.OUT,

    )
