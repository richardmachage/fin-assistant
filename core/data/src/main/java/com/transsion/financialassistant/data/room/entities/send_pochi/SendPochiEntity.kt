package com.transsion.financialassistant.data.room.entities.send_pochi

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.transsion.financialassistant.data.models.TransactionCategory

@Entity
data class SendPochiEntity(
    @PrimaryKey
    val transactionCode: String,
    val phone: String,
    val sentToName: String,
    val amount: Double,
    val mpesaBalance: Double,
    val transactionCost: Double,
    val date: String,
    val time: String,
    val transactionCategory: TransactionCategory = TransactionCategory.OUT
)