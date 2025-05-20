package com.transsion.financialassistant.data.room.entities.reversal_debit

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.transsion.financialassistant.data.models.TransactionCategory

@Entity
data class ReversalDebitEntity(
    @PrimaryKey
    val transactionCost: String,
    val phone: String,
    val transactionReversedCode: String,
    val amount: Double,
    val mpesaBalance: Double,
    val data: String,
    val time: String,
    //val transactionType : TransactionType. ,
    val transactionCategory: TransactionCategory = TransactionCategory.OUT,

    )
