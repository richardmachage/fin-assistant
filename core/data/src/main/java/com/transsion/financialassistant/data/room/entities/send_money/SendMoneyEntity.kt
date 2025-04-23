package com.transsion.financialassistant.data.room.entities.send_money

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType

@Entity
data class SendMoneyEntity(
    @PrimaryKey
    val transactionCode: String,
    val phone: String,
    val sentToName: String,
    val sentToPhone: String,
    val amount: Double,
    val mpesaBalance: Double,
    val transactionCost: Double,
    val date: String,
    val time: String,
    val transactionCategory: TransactionCategory = TransactionCategory.OUT,
    val transactionType: TransactionType = TransactionType.SEND_MONEY
)