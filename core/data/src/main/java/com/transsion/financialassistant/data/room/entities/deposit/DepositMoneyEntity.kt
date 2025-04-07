package com.transsion.financialassistant.data.room.entities.deposit

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType

@Entity
data class DepositMoneyEntity(
    @PrimaryKey
    val transactionCode: String,
    val phone: String,
    val amount: Double,
    val agentDepositedTo: String,
    val mpesaBalance: Double,
    val date: String,
    val startDate: String? = null,
    val endDate: String? = null,
    val time: String,
    val transactionCategory: TransactionCategory = TransactionCategory.IN,
    val transactionType: TransactionType = TransactionType.DEPOSIT
)
