package com.transsion.financialassistant.data.room.entities.withdraw

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType

@Entity
data class WithdrawMoneyEntity(
    @PrimaryKey
    val transactionCode: String,
    val phone: String,
    val amount: Double,
    val mpesaBalance: Double,
    val date: String,
    val startDate: String? = null,
    val endDate: String? = null,
    val time: String,
    val transactionCost: Double,
    val agent: String,
    val transactionCategory: TransactionCategory = TransactionCategory.OUT,
    val transactionType: TransactionType = TransactionType.WITHDRAWAL
)
