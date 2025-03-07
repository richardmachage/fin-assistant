package com.transsion.financialassistant.data.room.entities.withdraw

import androidx.room.PrimaryKey
import com.transsion.financialassistant.data.models.TransactionCategory

data class WithdrawMoneyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val transactionCode: String,
    val phone: String,
    val amount: Double,
    val mpesaBalance: Double,
    val date: String,
    val time: String,
    val transactionCategory: TransactionCategory = TransactionCategory.OUT
)
